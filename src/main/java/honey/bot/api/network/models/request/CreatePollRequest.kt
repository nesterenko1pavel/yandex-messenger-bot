package honey.bot.api.network.models.request

data class CreatePollRequest(
    val login: String? = null,
    val chatId: String? = null,
    val title: String,
    val answers: List<String>,
    val maxChoices: Long? = null,
    val isAnonymous: Boolean? = null,
    val payloadId: String? = null,
    val replyMessageId: Long? = null,
    val disableNotification: Boolean? = null,
    val important: Boolean? = null,
    val disableWebPagePreview: Boolean? = null,
    val threadId: Long? = null,
)
