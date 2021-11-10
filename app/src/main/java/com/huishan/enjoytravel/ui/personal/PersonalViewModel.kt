package com.huishan.enjoytravel.ui.personal

import android.view.View

import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(private val bikeRepository: Repository) : ViewModel() {

    fun gotoMessageCenter(view: View) {
        view.findNavController().navigate(R.id.message_center_fragment_dest)
    }

    fun gotoAboutUs(view: View) {
        view.findNavController().navigate(R.id.about_us_fragment_dest)
    }
}