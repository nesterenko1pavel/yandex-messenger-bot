package honey.bot.api.network.services

import honey.bot.api.network.annotations.Get
import honey.bot.api.network.annotations.Param
import honey.bot.api.network.annotations.Post
import honey.bot.api.network.models.response.PollResultsResponse
import honey.bot.api.network.models.response.PollVotersResponse
import honey.bot.api.network.models.response.SendPollResponse

interface PollService : ApiService {

    @Post("messages/createPoll/")
    fun createPoll(
        @Param("login") login: String?,
        @Param("chat_id") chatId: String?,
        @Param("title") title: String,
        @Param("answers") answers: List<String>,
        @Param("max_choices") maxChoices: Long?,
        @Param("is_anonymous") isAnonymous: Boolean?,
        @Param("payload_id") payloadId: String?,
        @Param("reply_message_id") replyMessageId: Long?,
        @Param("disable_notification") disableNotification: Boolean?,
        @Param("important") important: Boolean?,
        @Param("disable_web_page_preview") disableWebPagePreview: Boolean?,
        @Param("thread_id") threadId: Long?,
    ): SendPollResponse

    @Get("polls/getResults/")
    fun getPollResults(
        @Param("login") login: String?,
        @Param("chat_id") chatId: String?,
        @Param("message_id") messageId: Long,
        @Param("invite_hash") inviteHash: String?,
        @Param("thread_id") threadId: Long?,
    ): PollResultsResponse

    @Get("polls/getVoters/")
    fun getPollVoters(
        @Param("login") login: String?,
        @Param("chat_id") chatId: String?,
        @Param("message_id") messageId: Long,
        @Param("invite_hash") inviteHash: String?,
        @Param("limit") limit: Long?,
        @Param("cursor") cursor: Long?,
        @Param("answer_id") answerId: Long,
        @Param("thread_id") threadId: Long?,
    ): PollVotersResponse
}