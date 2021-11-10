package com.huishan.enjoytravel.ui.home.bikelist


import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.remote.entity.HistoryRecordEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryOrderRecordViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    var list = ArrayList<HistoryRecordEntity>()

}