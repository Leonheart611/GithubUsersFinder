package mikaoctofrentzen.com.githubusersfinder.domain

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private var retrofit: Retrofit? = null
    const val BASE_URL = "https://api.github.com/search/"
    const val TOKEN = "ebe75d7179327453898dbb3c877e0f22f37a31d0"

    fun getClient(): API {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .addInterceptor(CustomInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit!!.create(API::class.java)
    }

    class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", TOKEN)
                .build()
            return chain.proceed(request)
        }
    }
}

