package com.huishan.enjoytravel.ui.operation


import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.repository.Repository
import com.huishan.enjoytravel.ui.CommonScannerFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderTrackingViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    var bikeNumber: String = ""

    fun gotoScanBikeNumber(view: View) {
        val bundle = Bundle()
        bundle.putInt(CommonScannerFragment.SCAN_TAG, CommonScannerFragment.SCAN_TYPE_BIKE_NUMBER)
        view.findNavController().navigate(R.id.common_scanner_fragment_dest, bundle)
    }

    /**将bikeNumber传入*/
    fun gotoDetail(view: View) {
        view.findNavController().navigate(R.id.order_bike_detail_fragment_dest)
    }

    fun onBikeNumberChange(s: Editable?) {
        s?.let{
            bikeNumber = s.toString()
        }
    }
}