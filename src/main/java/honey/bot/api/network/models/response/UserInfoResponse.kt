package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("chat_link")
    val chatLink: String,
    @SerializedName("call_link")
    val callLink: String,
)