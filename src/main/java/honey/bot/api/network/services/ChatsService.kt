package honey.bot.api.network.services

import honey.bot.api.network.annotations.Param
import honey.bot.api.network.annotations.Post
import honey.bot.api.network.models.request.UserDto
import honey.bot.api.network.models.response.BasicApiResponse
import honey.bot.api.network.models.response.CreateChatResponse

interface ChatsService : ApiService {

    @Post("chats/create/")
    fun createChat(
        @Param("name") name: String,
        @Param("description") description: String,
        @Param("avatar_url") avatarUrl: String?,
        @Param("admins") admins: List<UserDto>?,
        @Param("members") members: List<UserDto>?,
        @Param("channel") channel: Boolean?,
        @Param("subscribers") subscribers: List<UserDto>?,
    ): CreateChatResponse

    @Post("chats/updateMembers/")
    fun updateChat(
        @Param("chat_id") chatId: String,
        @Param("members") members: List<UserDto>?,
        @Param("admins") admins: List<UserDto>?,
        @Param("subscribers") subscribers: List<UserDto>?,
        @Param("remove") remove: List<UserDto>?,
    ): BasicApiResponse
}