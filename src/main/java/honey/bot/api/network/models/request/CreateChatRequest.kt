package honey.bot.api.network.models.request

data class CreateChatRequest(
    val name: String,
    val description: String,
    val avatarUrl: String? = null,
    val admins: List<UserDto>? = null,
    val members: List<UserDto>? = null,
    val channel: Boolean? = null,
    val subscribers: List<UserDto>? = null,
)