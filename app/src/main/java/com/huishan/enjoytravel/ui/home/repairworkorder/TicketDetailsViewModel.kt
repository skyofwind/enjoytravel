package com.huishan.enjoytravel.ui.home.repairworkorder


import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketDetailsViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

}