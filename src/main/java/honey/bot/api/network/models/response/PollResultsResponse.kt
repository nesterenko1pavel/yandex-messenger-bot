package honey.bot.api.network.models.response

import com.google.gson.annotations.SerializedName

data class PollResultsResponse(
    val answers: Map<Long, Long>,
    @SerializedName("voted_count")
    val votedCount: Long,
)