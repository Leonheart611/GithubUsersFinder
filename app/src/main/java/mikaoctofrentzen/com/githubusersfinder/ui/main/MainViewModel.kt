package mikaoctofrentzen.com.githubusersfinder.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mikaoctofrentzen.com.githubusersfinder.model.Users
import mikaoctofrentzen.com.githubusersfinder.repository.UserRepository
import mikaoctofrentzen.com.githubusersfinder.util.Event

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val coroutineJob = Job()
    private val coroutineContext = Dispatchers.IO + coroutineJob
    private val uiScope = CoroutineScope(coroutineContext)

    private val _userResultLiveData by lazy { MutableLiveData<MutableList<Users>>() }
    val userResultLiveData: LiveData<MutableList<Users>> by lazy { _userResultLiveData }

    val showEmptyResult = MutableLiveData<Event<Boolean>>()


    fun getUsers(query: String = "Leonheart611", page: Int = 1) {
        try {
            uiScope.launch {
                val result = userRepository.getUsers(query = query, page = page)
                if (result.users.isNullOrEmpty().not()) {
                    _userResultLiveData.postValue(result.users.toMutableList())
                    Log.e("Result Success", result.users.toString())
                } else {
                    if (page == 1) {
                        showEmptyResult.postValue(Event(true))
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
            Log.e("Fail get Users", e.toString())
        }

    }
}