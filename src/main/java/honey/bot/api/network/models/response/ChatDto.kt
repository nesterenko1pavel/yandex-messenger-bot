package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class ChatDto(
    val type: ChatType,
    val id: String?,
) {
    enum class ChatType {

        @SerializedName("private")
        PRIVATE,

        @SerializedName("group")
        GROUP,

        @SerializedName("channel")
        CHANNEL;
    }
}