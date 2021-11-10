package com.huishan.enjoytravel.ui.login

import android.view.View
import androidx.lifecycle.*

import androidx.navigation.findNavController
import com.huishan.enjoytravel.Event
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.HttpConstants
import com.huishan.enjoytravel.data.remote.doFailure
import com.huishan.enjoytravel.data.remote.doSuccess
import com.huishan.enjoytravel.data.remote.entity.LoginResponseEntity
import com.huishan.enjoytravel.data.remote.request.LoginRequest
import com.huishan.enjoytravel.data.repository.Repository
import com.huishan.enjoytravel.util.SignUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    val sLoading = MutableLiveData<Boolean>(false)

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String> = _failure

    private val _login = MutableLiveData<LoginResponseEntity>()
    val login: LiveData<LoginResponseEntity> = _login

    val loginPhone = MutableLiveData<String>("18814129530")
    val loginPass = MutableLiveData<String>("123456")
    val checked = MutableLiveData<Boolean>(true)



    fun login() {
        if (loginPhone.value.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.tip_phone_empty)
            return
        }
        if (loginPass.value.isNullOrEmpty()) {
            _snackbarText.value = Event(R.string.tip_pass_empty)
            return
        }
        if (checked.value!!) {
            //_login.postValue(LoginResponseEntity(null, null, null))
            val request = LoginRequest(
                HttpConstants.LoginType.PASSWORD,
                loginPhone.value.toString(),
                loginPass.value.toString()
            )
            viewModelScope.launch(Dispatchers.IO) {
                bikeRepository.login(SignUtils.getSignMap(request))
                    .onStart {
                        sLoading.postValue(true)
                    }
                    .catch {
                        sLoading.postValue(false)
                    }
                    .onCompletion {
                        sLoading.postValue(false)
                    }
                    .collectLatest { result ->
                        result.doFailure { throwable ->
                            _failure.postValue(throwable?.message ?: "failure")
                        }
                        result.doSuccess { value ->
                            _login.postValue(value)
                        }
                    }
            }
        } else {
            _snackbarText.value = Event(R.string.tip_login_agree)
        }
    }
}