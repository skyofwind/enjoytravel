package com.huishan.enjoytravel.ui.operation

import android.view.View

import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OperationalStatisticsViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    fun gotoInspectionStatistics(view: View) {
        view.findNavController().navigate(R.id.inspection_statistics_fragment_dest)
    }

    fun gotoMaintenanceStatistics(view: View) {
        view.findNavController().navigate(R.id.maintenances_statistics_fragment_dest)
    }

    fun gotoMovingStatistics(view: View) {
        view.findNavController().navigate(R.id.moving_statistics_fragment_dest)
    }

    fun gotoPowerExchangeStatistics(view: View) {
        view.findNavController().navigate(R.id.power_exchange_statistics_fragment_dest)
    }

    fun gotoOrderTracking(view: View) {
        view.findNavController().navigate(R.id.order_tracking_fragment_dest)
    }

    fun gotoParkingLot(view: View) {
        view.findNavController().navigate(R.id.parking_lot_fragment_dest)
    }
}