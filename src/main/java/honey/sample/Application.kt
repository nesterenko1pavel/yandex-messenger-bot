package honey.sample

import honey.bot.api.BotController
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val token = "y0_AgAAAAB4pwHMAATIlgAAAAEQtH6dAAA5CTswbpZOQJOCKW3Hns7MImIWhA"
    val controller = BotController(token)
    launch { controller.startPolling() }
    controller.addUpdateListener { updates ->
        println(updates)
    }
    controller.sendText("Hello", "p.nestsiarenka@astondevs.ru")
}