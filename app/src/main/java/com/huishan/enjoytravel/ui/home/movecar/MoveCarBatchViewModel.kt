package com.huishan.enjoytravel.ui.home.movecar

import android.text.Editable
import android.view.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemMoveCarEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoveCarBatchViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
    private val _flashOpen = MutableLiveData<Boolean>(false)
    val flashOpen: LiveData<Boolean> = _flashOpen

    var scanCodeList = ArrayList<ItemMoveCarEntity>()
    var confirmList = ArrayList<ItemMoveCarEntity>()
    var completeList = ArrayList<ItemMoveCarEntity>()

    val scanCodeCarNumber = MutableLiveData<String>()

    /**批量挪车*/

    /**扫码挪车*/
    fun onScanCodeConfirm() {

    }

    fun onScanCodeItemChecked() {

    }

    /**批量挪车*/
    fun onAfterTextChange(s: Editable?) {

    }

    fun onComplete(view: View) {
        view.findNavController().navigate(R.id.move_car_complete_fragment_dest)
    }

    fun onConfirmItemChecked() {

    }

    fun flashChange(isChecked: Boolean) {
        _flashOpen.value = isChecked
    }
}