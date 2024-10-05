package honey.bot.api.network.models.request

data class PollResultsRequest(
    val login: String? = null,
    val chatId: String? = null,
    val messageId: Long,
    val inviteHash: String? = null,
    val threadId: Long? = null,
)