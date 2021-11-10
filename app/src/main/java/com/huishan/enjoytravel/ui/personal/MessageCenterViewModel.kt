package com.huishan.enjoytravel.ui.personal


import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.remote.entity.ItemMessageEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageCenterViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
    var entityList = ArrayList<ItemMessageEntity>()
}