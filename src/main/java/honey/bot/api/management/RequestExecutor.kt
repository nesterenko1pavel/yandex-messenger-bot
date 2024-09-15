package honey.bot.api.management

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

internal class RequestExecutor(
    private val httpClient: OkHttpClient,
    private val gson: Gson,
) {

    fun <T> execute(
        request: Request,
        returnType: Class<T>,
    ): T {
        // TODO: try to move to enqueue if possible
        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("HTTP Error ${response.code}")
        val body = response.body ?: throw IllegalStateException("ResponseBody is null")
        return gson.fromJson(body.string(), returnType)
    }
}