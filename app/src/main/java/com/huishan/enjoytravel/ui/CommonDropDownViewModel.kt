package com.huishan.enjoytravel.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.remote.entity.ItemDropDownCommon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * item_drop_down_common需要绑定一个具体的ViewModel
 */
@HiltViewModel
class CommonDropDownViewModel @Inject constructor(/*private val bikeRepository: Repository*/) :
    ViewModel() {
    var entityList: List<ItemDropDownCommon> = ArrayList<ItemDropDownCommon>()
    private val _targetItem = MutableLiveData<ItemDropDownCommon>()
    val targetItem: LiveData<ItemDropDownCommon> = _targetItem

    /**通过_targetItem.observe做出处理*/
    fun onCheckChange(view: View, isChecked: Boolean, item: ItemDropDownCommon) {
        if (isChecked && !item.checked) {
            item.checked = true
            resetOther(item)
            _targetItem.value = item
        }
    }

    private fun resetOther(item: ItemDropDownCommon) {
        for (i in entityList) {
            if (i != item && i.checked) {
                i.checked = false
            }
        }
    }
}