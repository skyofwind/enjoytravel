package com.huishan.enjoytravel.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataShareViewModel @Inject constructor(/*private val bikeRepository: Repository*/) : ViewModel() {
    private var bikeNumber: String = ""
    private var boxNumber: String = ""

    fun getBikeNumber(): String {
        return bikeNumber
    }

    fun resetBikeNumber(s: String) {
        bikeNumber = s
    }

    fun getBoxNumber(): String {
        return boxNumber
    }

    fun resetBoxNumber(s: String) {
        boxNumber = s
    }
}