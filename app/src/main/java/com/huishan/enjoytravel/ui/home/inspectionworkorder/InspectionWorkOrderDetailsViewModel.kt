package com.huishan.enjoytravel.ui.home.inspectionworkorder

import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InspectionWorkOrderDetailsViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
}