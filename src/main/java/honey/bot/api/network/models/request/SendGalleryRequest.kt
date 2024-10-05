package honey.bot.api.network.models.request

import java.io.File

data class SendGalleryRequest(
    val login: String? = null,
    val chatId: String? = null,
    val images: File, // File with images
    val threadId: Long? = null,
)