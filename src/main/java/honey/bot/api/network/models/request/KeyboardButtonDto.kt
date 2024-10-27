package honey.bot.api.network.models.request

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class KeyboardButtonDto(
    val text: String,
    @SerializedName("callback_data")
    val callbackData: JsonObject?,
)