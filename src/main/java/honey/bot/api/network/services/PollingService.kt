package honey.bot.api.network.services

import honey.bot.api.network.annotations.Get
import honey.bot.api.network.annotations.Param
import honey.bot.api.network.models.response.GeneralUpdateResponse

interface PollingService : ApiService {

    @Get("messages/getUpdates/")
    fun getUpdates(
        @Param("limit") limit: Long,
        @Param("offset") offset: Long,
    ): GeneralUpdateResponse
}