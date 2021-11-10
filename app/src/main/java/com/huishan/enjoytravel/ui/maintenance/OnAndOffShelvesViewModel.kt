package com.huishan.enjoytravel.ui.maintenance


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huishan.enjoytravel.data.remote.entity.ItemOnAndOffShelvesEntity
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnAndOffShelvesViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {
    var list = ArrayList<ItemOnAndOffShelvesEntity>()

    private val _flashOpen = MutableLiveData<Boolean>(false)
    val flashOpen: LiveData<Boolean> = _flashOpen
}