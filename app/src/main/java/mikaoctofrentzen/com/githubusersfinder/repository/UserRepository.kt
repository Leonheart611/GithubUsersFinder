package mikaoctofrentzen.com.githubusersfinder.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import mikaoctofrentzen.com.githubusersfinder.domain.API
import mikaoctofrentzen.com.githubusersfinder.model.GithubUserResponse
import retrofit2.Response

interface UserRepository {
    suspend fun getUsers(query: String, per_page: Int = 20, page: Int): Response<GithubUserResponse>
}

class UserRepositoryImpl(val api: API) : UserRepository {
    val coroutineJob = Job()
    val coroutineContext = Dispatchers.IO + coroutineJob
    val uiScope = CoroutineScope(coroutineContext)

    override suspend fun getUsers(
        query: String,
        per_page: Int,
        page: Int
    ): Response<GithubUserResponse> = uiScope.run {
        api.getUsers(query, per_page, page)
    }

}