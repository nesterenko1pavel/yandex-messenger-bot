package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class CreateChatResponse(
    @SerializedName("chat_id")
    val chatId: String,
)