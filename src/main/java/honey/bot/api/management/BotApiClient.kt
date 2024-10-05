package honey.bot.api.management

import honey.bot.api.network.models.request.*
import honey.bot.api.network.models.response.*
import honey.bot.api.network.services.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class BotApiClient(
    private val pollingService: PollingService,
    private val messagesService: MessagesService,
    private val chatsService: ChatsService,
    private val userService: UserService,
    private val pollService: PollService,
) {

    fun getUpdates(limit: Long, offset: Long): GeneralUpdateResponse {
        return pollingService.getUpdates(limit, offset)
    }

    fun sendText(request: SendTextRequest): SendMessageResponse {
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
            request.inlineKeyboard,
        )
    }

    fun sendFile(request: SendFileRequest): SendMessageResponse {
        return messagesService.sendFile(
            request.document,
            request.login,
            request.chatId,
            request.threadId,
        )
    }

    fun getFile(fileName: String, fileExtension: String, fileId: String): Path {
        val byteArray = messagesService.getFile(fileId)
        return Files.write(File("$fileName.$fileExtension").toPath(), byteArray)
    }

    fun sendImage(request: SendImageRequest): SendImageResponse {
        return messagesService.sendImage(
            request.login,
            request.chatId,
            request.image,
            request.threadId,
        )
    }

    fun sendGallery(request: SendGalleryRequest): SendGalleryResponse {
        throw UnsupportedOperationException("At the moment we cant send directory with images")
        return messagesService.sendGallery(
            request.login,
            request.chatId,
            request.images,
            request.threadId,
        )
    }

    fun deleteMessage(request: DeleteMessageRequest): DeleteMessageResponse {
        return messagesService.deleteMessage(
            request.login,
            request.chatId,
            request.messageId,
            request.threadId,
        )
    }

    fun createChat(request: CreateChatRequest): CreateChatResponse {
        return chatsService.createChat(
            request.name,
            request.description,
            request.avatarUrl,
            request.admins,
            request.members,
            request.channel,
            request.subscribers,
        )
    }

    fun updateChat(request: UpdateChatRequest): BasicApiResponse {
        return chatsService.updateChat(
            request.chatId,
            request.members,
            request.admins,
            request.subscribers,
            request.remove,
        )
    }

    fun getUserInfo(login: String): UserInfoResponse {
        return userService.getUserInfo(login)
    }

    fun createPoll(request: CreatePollRequest): SendPollResponse {
        return pollService.createPoll(
            request.login,
            request.chatId,
            request.title,
            request.answers,
            request.maxChoices,
            request.isAnonymous,
            request.payloadId,
            request.replyMessageId,
            request.disableNotification,
            request.important,
            request.disableWebPagePreview,
            request.threadId,
        )
    }

    fun getPollResults(request: PollResultsRequest): PollResultsResponse {
        return pollService.getPollResults(
            request.login,
            request.chatId,
            request.messageId,
            request.inviteHash,
            request.threadId,
        )
    }

    fun getPollVoters(request: PollVotersRequest): PollVotersResponse {
        return pollService.getPollVoters(
            request.login,
            request.chatId,
            request.messageId,
            request.inviteHash,
            request.limit,
            request.cursor,
            request.answerId,
            request.threadId,
        )
    }
}