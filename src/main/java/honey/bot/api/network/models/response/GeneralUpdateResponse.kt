package honey.bot.api.network.models.response

data class GeneralUpdateResponse(
    override val ok: Boolean,
    override val description: String?,
    val updates: List<UpdateDto>,
) : ApiResponse(ok, description)