package com.huishan.enjoytravel.ui.home.movecar


import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoveCarSingleViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    fun gotoScanner(view: View) {
        view.findNavController().navigate(R.id.common_scanner_fragment_dest)
    }

    /**拍照的还没做，可以叫UI再给个一张完整的默认图片，如要选择多个，则改成使用RecyclerView*/
    fun gotoPhoto(view: View) {

    }
}