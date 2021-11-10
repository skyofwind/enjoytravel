package com.huishan.enjoytravel.ui.home.repairworkorder


import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BikeRepairViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    fun gotoScanner(view: View) {
        view.findNavController().navigate(R.id.common_scanner_fragment_dest)
    }
}