package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class UpdateDto(
    val from: SenderDto,
    val chat: ChatDto,
    val text: String?,
    val timestamp: Long,
    @SerializedName("message_id")
    val messageId: Long,
    @SerializedName("update_id")
    val updateId: Long,
    val file: FileDto?,
    val images: List<ImageDto>?,
)