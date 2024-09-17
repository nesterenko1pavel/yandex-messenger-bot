package honey.bot.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import honey.bot.api.management.ApiImplementation
import honey.bot.api.management.FibonacciFetcher
import honey.bot.api.management.RequestExecutor
import honey.bot.api.network.models.KeyboardButtonDto
import honey.bot.api.network.models.SendMessageResponse
import honey.bot.api.network.models.UpdateDto
import honey.bot.api.network.services.MessagesService
import honey.bot.api.network.services.PollingService
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

private const val BASE_URL = "https://botapi.messenger.yandex.net/bot/v1/"
private const val READ_DEFAULT_TIMEOUT = 30L

class BotController(
    token: String,
    gson: Gson = GsonBuilder().create(),
    httpClient: OkHttpClient = OkHttpClient.Builder().readTimeout(READ_DEFAULT_TIMEOUT, TimeUnit.SECONDS).build(),
) {
    private val updateListeners = ConcurrentLinkedQueue<UpdateListener>()
    private val isStarted = AtomicBoolean(false)

    private val fetcher: FibonacciFetcher = FibonacciFetcher()
    private val apiImplementation = ApiImplementation(
        gson = gson,
        requestExecutor = RequestExecutor(httpClient, gson),
        baseUrl = BASE_URL,
        token = token
    )

    private val pollingService = apiImplementation.createImplementation<PollingService>()
    private val messagesService = apiImplementation.createImplementation<MessagesService>()

    suspend fun startPolling() {
        if (isStarted.get()) throw IllegalStateException("Polling already started")
        isStarted.set(true)

        var offset = 0L
        while (isStarted.get()) {
            fetcher.execute {
                val response = pollingService.getUpdates(100, offset)
                val maxUpdateId = response.updates.maxOfOrNull { it.updateId }
                offset = if (maxUpdateId != null) {
                    maxUpdateId + 1
                } else {
                    0
                }
                invokeListeners(response.updates)
            }
        }
    }

    fun stopPolling() {
        isStarted.set(false)
    }

    private fun invokeListeners(updates: List<UpdateDto>) {
        for (listener in updateListeners) {
            listener.onNewUpdate(updates)
        }
    }

    fun addUpdateListener(listener: UpdateListener) {
        updateListeners.add(listener)
    }

    // TODO: add exception handling
    fun sendText(
        text: String,
        login: String? = null,
        chatId: String? = null,
        payloadId: String? = null,
        replyMessageId: Long? = null,
        disableNotification: Boolean? = null,
        important: Boolean? = null,
        disableWebPagePreview: Boolean? = null,
        threadId: Long? = null,
        inlineKeyboard: List<KeyboardButtonDto>? = null,
    ): SendMessageResponse {
        return messagesService.sendText(
            text,
            login,
            chatId,
            payloadId,
            replyMessageId,
            disableNotification,
            important,
            disableWebPagePreview,
            threadId,
            inlineKeyboard
        )
    }

    fun sendFile(
        login: String? = null,
        chatId: String? = null,
        document: File,
        threadId: Long? = null,
    ): SendMessageResponse {
        return messagesService.sendFile(login, chatId, document, threadId)
    }
}

fun interface UpdateListener {

    fun onNewUpdate(update: List<UpdateDto>)
}