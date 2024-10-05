package honey.bot.api.network.models.request

data class PollVotersRequest(
    val login: String? = null,
    val chatId: String? = null,
    val messageId: Long,
    val inviteHash: String? = null,
    val limit: Long? = null,
    val cursor: Long? = null,
    val answerId: Long,
    val threadId: Long? = null,
)