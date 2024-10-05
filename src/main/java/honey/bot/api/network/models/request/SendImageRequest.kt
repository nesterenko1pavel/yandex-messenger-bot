package honey.bot.api.network.models.request

import java.io.File

data class SendImageRequest(
    val login: String? = null,
    val chatId: String? = null,
    val image: File, // single File with image
    val threadId: Long? = null,
)