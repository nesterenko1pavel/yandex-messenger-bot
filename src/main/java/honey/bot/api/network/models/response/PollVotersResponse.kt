package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class PollVotersResponse(
    @SerializedName("answer_id")
    val answerId: Long,
    @SerializedName("voted_count")
    val votedCount: Long,
    val cursor: CursorDto,
    val votes: List<VoteDto>,
)