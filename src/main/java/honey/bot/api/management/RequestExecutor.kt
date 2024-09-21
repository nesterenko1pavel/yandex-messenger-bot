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
        httpClient.newCall(request)
            .execute()
            .use { response ->
                val body = response.body ?: throw IllegalStateException("ResponseBody is null")
                val bodyString = body.string()
                if (!response.isSuccessful) throw IOException("HTTP Error ${response.code} $bodyString}")
                return gson.fromJson(bodyString, returnType)
            }
    }
}