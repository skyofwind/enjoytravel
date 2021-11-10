package com.huishan.enjoytravel.ui.home.scanner


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InputBikeNumberViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    val bikeNumber = MutableLiveData<String>()

    private val _confirm = MutableLiveData<Boolean>()
    val confirm: LiveData<Boolean> = _confirm

    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String> = _failure

    fun onConfirm() {
        if (bikeNumber.value.isNullOrEmpty()) {
            _failure.value = "请输入车牌号"
        } else {
            if (bikeNumber.value!!.length < 9) {
                _failure.value = "车牌号不完整，请补齐"
            } else {
                _confirm.value = true
            }
        }
    }

}