package honey.bot.api.network.models.request

data class SendTextRequest(
    val text: String,
    val login: String? = null,
    val chatId: String? = null,
    val payloadId: String? = null,
    val replyMessageId: Long? = null,
    val disableNotification: Boolean? = null,
    val important: Boolean? = null,
    val disableWebPagePreview: Boolean? = null,
    val threadId: Long? = null,
    val inlineKeyboard: List<KeyboardButtonDto>? = null,
)