package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class SendMessageResponse(
    override val ok: Boolean,
    override val description: String?,
    @SerializedName("message_id")
    val messageId: Long,
) : ApiResponse(ok, description)