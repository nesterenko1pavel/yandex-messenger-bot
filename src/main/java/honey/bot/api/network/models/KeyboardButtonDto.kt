package honey.bot.api.network.models

import com.google.gson.annotations.SerializedName

data class KeyboardButtonDto(
    val text: String,
    @SerializedName("callback_data")
    val callbackData: String?, // json
)