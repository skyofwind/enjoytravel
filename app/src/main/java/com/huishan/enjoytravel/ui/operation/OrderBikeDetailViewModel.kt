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
class OrderBikeDetailViewModel @Inject constructor(private val bikeRepository: Repository): ViewModel() {

    fun gotoHistoryOrder(view: View) {
        view.findNavController().navigate(R.id.history_order_record_fragment_dest)
    }
}