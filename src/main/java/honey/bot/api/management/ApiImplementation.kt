package honey.bot.api.management

import com.google.gson.Gson
import honey.bot.api.network.annotations.Get
import honey.bot.api.network.annotations.Param
import honey.bot.api.network.annotations.Post
import honey.bot.api.network.services.ApiService
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.reflect.Array.get
import java.lang.reflect.Array.getLength
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.nio.file.Files

internal class ApiImplementation(
    val gson: Gson,
    val requestExecutor: RequestExecutor,
    private val baseUrl: String,
    private val token: String,
) {

    inline fun <reified T : ApiService> createImplementation(): T {
        val impl = Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf<Class<*>>(T::class.java)
        ) { _: Any, method: Method, args: Array<Any?>? ->
            val queries = ParamExtractor(gson, method, args).getParameters()
            val request = createRequest(method, queries)
            requestExecutor.execute(request, method.returnType)
        }
        return impl as T
    }

    fun createRequest(method: Method, queries: List<QueryEntity>): Request {
        val getAnnotation = method.getAnnotation(Get::class.java)
        if (getAnnotation != null) {
            return createGetRequest(getAnnotation.endpoint, queries)
        }
        val postAnnotation = method.getAnnotation(Post::class.java)
        if (postAnnotation != null) {
            return createPostRequest(postAnnotation.endpoint, queries)
        }
        throw IllegalStateException("Method $method should be annotated by REST METHOD annotation")
    }

    private fun createGetRequest(path: String, queries: List<QueryEntity>): Request {
        val urlBuilder = baseUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment(path)
        queries.forEach { query -> urlBuilder.addQueryParameter(query.key, query.valueAsString) }
        return Request.Builder()
            .url(urlBuilder.build())
            .addHeader("Authorization", "OAuth $token")
            .build()
    }

    private fun createPostRequest(path: String, queries: List<QueryEntity>): Request {
        val urlBuilder = baseUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment(path)
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        queries.forEach { query ->
            if (query.value is File) {
                val mediaType = "application/json".toMediaTypeOrNull()
                val requestBody = Files.readAllBytes(query.value.toPath()).toRequestBody(mediaType)
                builder.addFormDataPart(query.key, query.value.name, requestBody)
            } else {
                builder.addFormDataPart(query.key, query.value.toString())
            }
        }
        return Request.Builder()
            .url(urlBuilder.build())
            .post(builder.build())
            .addHeader("Authorization", "OAuth $token")
            .build()
    }
}

internal class ParamExtractor(
    private val gson: Gson,
    private val method: Method,
    private val args: Array<Any?>?,
) {
    fun getParameters(): List<QueryEntity> {
        if (args == null) return emptyList()

        return args.flatMapIndexed { index, arg ->
            if (arg != null) {
                val paramName = findParamName(index)
                findParamValues(arg).map { value ->
                    QueryEntity(gson, paramName, value)
                }
            } else {
                emptyList()
            }
        }
    }

    private fun findParamName(index: Int): String {
        method.parameterAnnotations[index].forEach { annotation ->
            if (annotation is Param) {
                return annotation.paramName
            }
        }
        throw IllegalStateException("QueryParam not found in $method with $args")
    }

    private fun findParamValues(arg: Any): List<Any> {
        if (!arg.javaClass.isArray) return listOf(arg)

        val length = getLength(arg)
        val list: MutableList<Any> = ArrayList(length)
        for (i in 0 until length) {
            list.add(get(arg, i))
        }
        return list
    }
}