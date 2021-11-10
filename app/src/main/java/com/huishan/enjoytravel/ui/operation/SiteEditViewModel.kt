package com.huishan.enjoytravel.ui.operation


import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SiteEditViewModel @Inject constructor(private val bikeRepository: Repository): ViewModel() {
}