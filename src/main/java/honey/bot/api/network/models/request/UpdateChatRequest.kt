package honey.bot.api.network.models.request

data class UpdateChatRequest(
    val chatId: String,
    val members: List<UserDto>? = null,
    val admins: List<UserDto>? = null,
    val subscribers: List<UserDto>? = null,
    val remove: List<UserDto>? = null,
)