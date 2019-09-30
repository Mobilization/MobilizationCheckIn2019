package pl.saramak.mobilizationcheckin2019

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.KotlinLogging
import pl.saramak.core.ui.BaseViewModel
import pl.saramak.core.ui.data.ResError
import pl.saramak.core.ui.data.Response
import pl.saramak.core.ui.data.Success
import pl.saramak.data.User

import java.lang.Exception

class MainViewModel(private val loginService: LoginService) : BaseViewModel() {

    private val logger = KotlinLogging.logger {}

    val user = MutableLiveData<Response<User>>()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginUser = loginService.login(email, password)
                user.postValue(Success(loginUser))
            }catch (e:Exception){
                user.postValue(ResError(e))
            }
        }
    }

}
