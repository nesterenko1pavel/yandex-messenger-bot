package honey.bot.api.network.services

import honey.bot.api.network.annotations.Get
import honey.bot.api.network.annotations.Param
import honey.bot.api.network.models.response.UserInfoResponse

interface UserService : ApiService {

    @Get("users/getUserLink/")
    fun getUserInfo(
        @Param("login") login: String,
    ): UserInfoResponse
}