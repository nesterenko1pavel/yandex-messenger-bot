package honey.bot.api.network.models.response

data class BasicApiResponse(
    override val ok: Boolean,
    override val description: String?,
) : ApiResponse(ok, description)