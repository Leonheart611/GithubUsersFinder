package mikaoctofrentzen.com.githubusersfinder.domain

import mikaoctofrentzen.com.githubusersfinder.model.GithubUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("users")
    suspend fun getUsers(
        @Query("q") query: String,
        @Query("per_page") per_page: Int = 20,
        @Query("page") page: Int
    ): Response<GithubUserResponse>
}