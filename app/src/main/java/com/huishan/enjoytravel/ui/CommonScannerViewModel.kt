package com.huishan.enjoytravel.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommonScannerViewModel @Inject constructor(/*private val bikeRepository: Repository*/) : ViewModel() {
    private val _flashOpen = MutableLiveData<Boolean>(false)
    val flashOpen: LiveData<Boolean> = _flashOpen

    fun flashChange(isChecked: Boolean) {
        _flashOpen.value = isChecked
    }
}