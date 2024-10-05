package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class SendPollResponse(
    @SerializedName("message_id")
    val messageId: Long,
)