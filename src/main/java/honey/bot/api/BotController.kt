package honey.bot.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import honey.bot.api.management.ApiImplementation
import honey.bot.api.management.BotApiClient
import honey.bot.api.management.FibonacciExecutor
import honey.bot.api.management.RequestExecutor
import honey.bot.api.network.models.response.UpdateDto
import honey.bot.api.network.services.*
import okhttp3.OkHttpClient
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

private const val BASE_URL = "https://botapi.messenger.yandex.net/bot/v1/"
private const val READ_DEFAULT_TIMEOUT = 30L

class BotController(
    token: String,
    gson: Gson = GsonBuilder().create(),
    httpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(READ_DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .build(),
) {
    private val updateListeners = ConcurrentLinkedQueue<UpdateListener>()
    private val isStarted = AtomicBoolean(false)

    private val fetcher: FibonacciExecutor = FibonacciExecutor()
    private val apiImplementation = ApiImplementation(
        gson = gson,
        requestExecutor = RequestExecutor(httpClient, gson),
        baseUrl = BASE_URL,
        token = token
    )

    val botApiClient by lazy {
        BotApiClient(
            apiImplementation.createImplementation<PollingService>(),
            apiImplementation.createImplementation<MessagesService>(),
            apiImplementation.createImplementation<ChatsService>(),
            apiImplementation.createImplementation<UserService>(),
            apiImplementation.createImplementation<PollService>(),
        )
    }

    suspend fun startPolling() {
        if (isStarted.get()) throw IllegalStateException("Polling already started")
        isStarted.set(true)

        var offset = 0L
        while (isStarted.get()) {
            fetcher.execute {
                val response = botApiClient.getUpdates(100L, offset)
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
}

fun interface UpdateListener {

    fun onNewUpdate(update: List<UpdateDto>)
}