package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class CreateChatResponse(
    override val ok: Boolean,
    override val description: String?,
    @SerializedName("chat_id")
    val chatId: String,
): ApiResponse(ok, description)