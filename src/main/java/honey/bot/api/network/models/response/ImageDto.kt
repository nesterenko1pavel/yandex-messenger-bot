package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("file_id")
    val fileId: String,
    val width: Long,
    val height: Long,
    val size: Long?,
    val name: String?,
)