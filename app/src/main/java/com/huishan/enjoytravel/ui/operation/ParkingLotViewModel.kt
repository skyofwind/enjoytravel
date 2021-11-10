package com.huishan.enjoytravel.ui.operation


import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemParkingLotItemEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ParkingLotViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
    var entityList = ArrayList<ItemParkingLotItemEntity>()

    fun gotoEdit(view: View, item: ItemParkingLotItemEntity) {
        view.findNavController().navigate(R.id.site_edit_fragment_dest)
    }
}