package com.huishan.enjoytravel.ui.home

import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServiceSelectViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

}