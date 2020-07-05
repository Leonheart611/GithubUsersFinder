package mikaoctofrentzen.com.githubusersfinder.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import mikaoctofrentzen.com.githubusersfinder.domain.API
import mikaoctofrentzen.com.githubusersfinder.domain.RetrofitBuilder
import mikaoctofrentzen.com.githubusersfinder.model.GithubUserResponse
import retrofit2.Response

interface UserRepository {
    suspend fun getUsers(query: String, per_page: Int = 20, page: Int): GithubUserResponse
}

class UserRepositoryImpl(val retrofitBuilde: RetrofitBuilder) : UserRepository {
    private val retrofitApi = retrofitBuilde.getAPI()

    override suspend fun getUsers(
        query: String,
        per_page: Int,
        page: Int
    ): GithubUserResponse = run {
        retrofitApi.getUsers(query, per_page, page)
    }

}