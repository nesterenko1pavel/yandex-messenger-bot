package honey.bot.api.management

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class FibonacciExecutor {

    private val mutex = Mutex()
    private val delayState = MutableDelayState()

    suspend inline fun execute(action: () -> Unit) {
        var exception: Exception? = null
        try {
            action()
        } catch (e: Exception) {
            exception = e
            e.printStackTrace() // TODO: add correct logging system
            currentCoroutineContext().ensureActive()
            smartWait()
        } finally {
            if (exception == null) {
                resetState()
            }
        }
    }

    private suspend fun smartWait() {
        delay(delayState.currentDelay)
        mutex.withLock { delayState.updateDelay() }
    }

    private fun resetState() {
        delayState.resetState()
    }
}

const val DEFAULT_BEFORE_LAST = 0L
const val DEFAULT_LAST = 1L
const val DEFAULT_CURRENT = 1L

private class MutableDelayState(
    private var beforeLast: Long = DEFAULT_BEFORE_LAST,
    private var last: Long = DEFAULT_LAST,
    private var current: Long = DEFAULT_CURRENT,
) {
    val currentDelay get() = current

    fun updateDelay() {
        beforeLast = last
        last = current
        current = beforeLast + last
    }

    fun resetState() {
        beforeLast = DEFAULT_BEFORE_LAST
        last = DEFAULT_LAST
        current = DEFAULT_CURRENT
    }
}