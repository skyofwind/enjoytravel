package com.huishan.enjoytravel.ui.maintenance


import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.repository.Repository
import com.huishan.enjoytravel.ui.CommonScannerFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VehicleBoxBindingViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    fun gotoScanBikeNumber(view: View) {
        val bundle = Bundle()
        bundle.putInt(CommonScannerFragment.SCAN_TAG, CommonScannerFragment.SCAN_TYPE_BIKE_NUMBER)
        view.findNavController().navigate(R.id.common_scanner_fragment_dest, bundle)
    }

    fun gotoScanBoxNumber(view: View) {
        val bundle = Bundle()
        bundle.putInt(CommonScannerFragment.SCAN_TAG, CommonScannerFragment.SCAN_TYPE_BOX_NUMBER)
        view.findNavController().navigate(R.id.common_scanner_fragment_dest, bundle)
    }
}