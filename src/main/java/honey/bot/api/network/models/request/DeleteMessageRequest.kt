package honey.bot.api.network.models.request

data class DeleteMessageRequest(
    val login: String? = null,
    val chatId: String? = null,
    val messageId: Long,
    val threadId: Long? = null,
)