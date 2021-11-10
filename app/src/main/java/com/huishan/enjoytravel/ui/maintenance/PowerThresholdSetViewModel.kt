package com.huishan.enjoytravel.ui.maintenance

import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.remote.entity.ItemPowerThresholdSetEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PowerThresholdSetViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
    var list = ArrayList<ItemPowerThresholdSetEntity>()

    /**进度条改变时视需求进行设置换电阈值请求*/
    fun onSeekBarChange(seekbar: SeekBar, progress: Int, from: Boolean, s: ItemPowerThresholdSetEntity) {

    }
}