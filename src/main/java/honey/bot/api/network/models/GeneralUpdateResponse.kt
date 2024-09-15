package honey.bot.api.network.models

import com.google.gson.annotations.SerializedName

data class GeneralUpdateResponse(
    val updates: List<UpdateDto>,
)

data class SendTextResponse(
    val ok: Boolean,
    @SerializedName("message_id")
    val messageId: Long,
)