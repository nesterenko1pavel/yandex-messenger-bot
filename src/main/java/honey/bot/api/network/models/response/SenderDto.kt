package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class SenderDto(
    @SerializedName("login", alternate = ["id"])
    val id: String,
    @SerializedName("display_name")
    val displayName: String,
    val robot: Boolean,
)