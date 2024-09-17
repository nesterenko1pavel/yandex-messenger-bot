package honey.bot.api.network.services

import honey.bot.api.network.annotations.Param
import honey.bot.api.network.annotations.Post
import honey.bot.api.network.models.KeyboardButtonDto
import honey.bot.api.network.models.SendMessageResponse
import java.io.File

internal interface MessagesService : ApiService {

    @Post("messages/sendText/")
    fun sendText(
        @Param("text") text: String,
        @Param("login") login: String?,
        @Param("chat_id") chatId: String?,
        @Param("payload_id") payloadId: String?,
        @Param("reply_message_id") replyMessageId: Long?,
        @Param("disable_notification") disableNotification: Boolean?,
        @Param("important") important: Boolean?,
        @Param("disable_web_page_preview") disableWebPagePreview: Boolean?,
        @Param("thread_id") threadId: Long?,
        @Param("inline_keyboard") inlineKeyboard: List<KeyboardButtonDto>?,
    ): SendMessageResponse

    @Post("messages/sendFile/")
    fun sendFile(
        @Param("login") login: String?,
        @Param("chat_id") chatId: String?,
        @Param("document") document: File,
        @Param("thread_id") threadId: Long?,
    ): SendMessageResponse
}