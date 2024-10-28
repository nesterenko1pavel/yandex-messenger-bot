package honey.bot.api.management

import com.google.gson.Gson
import com.google.gson.JsonObject
import honey.bot.api.network.annotations.Get
import honey.bot.api.network.annotations.Multipart
import honey.bot.api.network.annotations.Param
import honey.bot.api.network.annotations.Post
import honey.bot.api.network.services.ApiService
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
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
            return createPostRequest(
                postAnnotation.endpoint,
                queries,
                isMultipart = method.getAnnotation(Multipart::class.java) != null
            )
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

    private fun createPostRequest(path: String, queries: List<QueryEntity>, isMultipart: Boolean): Request {
        val urlBuilder = baseUrl.toHttpUrl()
            .newBuilder()
            .addPathSegment(path)

        val multipartBodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val jsonObject = JsonObject()

        queries.forEach { query ->
            when (query.value) {
                is File -> {
                    val requestBody = Files.readAllBytes(query.value.toPath()).toRequestBody()
                    multipartBodyBuilder.addFormDataPart(query.key, query.value.name, requestBody)
                }
                is List<*> -> {
                    if (isMultipart) {
                        multipartBodyBuilder.addPart(query.valueAsJson.toMultipartBodyPart("${query.key}[]"))
                    } else {
                        jsonObject.addProperty(query.key, query.valueAsJson)
                    }
                }
                else -> {
                    if (isMultipart) {
                        multipartBodyBuilder.addFormDataPart(query.key, query.value.toString())
                    } else {
                        jsonObject.addProperty(query.key, query.value.toString())
                    }
                }
            }
        }

        val requestBody = if (isMultipart) {
            multipartBodyBuilder.build()
        } else {
            val formattedJson = jsonObject.toString()
                .replace("\\\"", "\"")
                .replace("\"[", "[")
                .replace("]\"", "]")
            val mediaType = "application/json; charset=utf-8".toMediaType()
            formattedJson.toRequestBody(mediaType)
        }

        return Request.Builder()
            .url(urlBuilder.build())
            .post(requestBody)
            .addHeader("Authorization", "OAuth $token")
            .build()
    }

    private fun String.toMultipartBodyPart(partName: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(partName, this)
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