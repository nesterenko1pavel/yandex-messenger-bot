package honey.bot.api.management

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class FibonacciFetcher {

    private val mutex = Mutex()
    private val delayState = MutableDelayState()

    suspend inline fun execute(action: () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            e.printStackTrace() // TODO: add correct logging system
            currentCoroutineContext().ensureActive()
            smartWait()
        }
    }

    private suspend fun smartWait() {
        delay(delayState.currentDelay)
        mutex.withLock { delayState.updateDelay() }
    }
}

private class MutableDelayState(
    private var beforeLast: Long = 0L,
    private var last: Long = 1L,
    private var current: Long = 1L,
) {
    val currentDelay get() = current

    fun updateDelay() {
        beforeLast = last
        last = current
        current = beforeLast + last
    }
}