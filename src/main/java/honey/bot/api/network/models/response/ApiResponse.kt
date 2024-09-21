package honey.bot.api.network.models.response

abstract class ApiResponse(
    @Transient open val ok: Boolean,
    @Transient open val description: String?,
)