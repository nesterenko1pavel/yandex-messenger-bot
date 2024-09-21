package honey.bot.api.management

import honey.bot.api.network.models.request.CreateChatRequest
import honey.bot.api.network.models.request.SendFileRequest
import honey.bot.api.network.models.request.SendTextRequest
import honey.bot.api.network.models.request.UpdateChatRequest
import honey.bot.api.network.models.response.ApiResponse
import honey.bot.api.network.models.response.CreateChatResponse
import honey.bot.api.network.models.response.GeneralUpdateResponse
import honey.bot.api.network.models.response.SendMessageResponse
import honey.bot.api.network.services.ChatsService
import honey.bot.api.network.services.MessagesService
import honey.bot.api.network.services.PollingService

class BotApiClient(
    private val pollingService: PollingService,
    private val messagesService: MessagesService,
    private val chatsService: ChatsService,
) {

    fun getUpdates(limit: Long, offset: Long): GeneralUpdateResponse {
        return pollingService.getUpdates(limit, offset)
    }

    fun sendText(
        request: SendTextRequest,
    ): SendMessageResponse {
        return messagesService.sendText(
            request.text,
            request.login,
            request.chatId,
            request.payloadId,
            request.replyMessageId,
            request.disableNotification,
            request.important,
            request.disableWebPagePreview,
            request.threadId,
            request.inlineKeyboard
        )
    }

    fun sendFile(
        request: SendFileRequest,
    ): SendMessageResponse {
        return messagesService.sendFile(request.document, request.login, request.chatId, request.threadId)
    }

    fun createChat(
        request: CreateChatRequest,
    ): CreateChatResponse {
        return chatsService.createChat(
            request.name,
            request.description,
            request.avatarUrl,
            request.admins,
            request.members,
            request.channel,
            request.subscribers
        )
    }

    fun updateChat(
        request: UpdateChatRequest,
    ): ApiResponse {
        return chatsService.updateChat(
            request.chatId,
            request.members,
            request.admins,
            request.subscribers,
            request.remove
        )
    }
}