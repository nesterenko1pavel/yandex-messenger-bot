package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class SendMessageResponse(
    @SerializedName("message_id")
    val messageId: Long,
)