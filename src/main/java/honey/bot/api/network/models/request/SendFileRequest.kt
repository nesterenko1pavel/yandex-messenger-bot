package honey.bot.api.network.models.request

import java.io.File

data class SendFileRequest(
    val document: File,
    val login: String? = null,
    val chatId: String? = null,
    val threadId: Long? = null,
)