package mikaoctofrentzen.com.githubusersfinder.module

import mikaoctofrentzen.com.githubusersfinder.domain.RetrofitBuilder
import mikaoctofrentzen.com.githubusersfinder.repository.UserRepository
import mikaoctofrentzen.com.githubusersfinder.repository.UserRepositoryImpl
import mikaoctofrentzen.com.githubusersfinder.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val injectionModule = module {
    single { RetrofitBuilder() }
    single<UserRepository> { UserRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }
}