package com.huishan.enjoytravel

import android.app.Application
import com.clj.fastble.BleManager
import com.huishan.enjoytravel.ui.FragmentBackStatus
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BikeApplication : Application() {

    companion object {
        var fragmentBackStatus: FragmentBackStatus = FragmentBackStatus.EXIT_APP
    }

    override fun onCreate() {
        super.onCreate()
        initBle()
    }

    /**正式环境下enableLog(false)*/
    fun initBle() {
        BleManager.getInstance().init(this)
        BleManager.getInstance().enableLog(true)
            .setReConnectCount(1, 5000)
            .setOperateTimeout(5000)

    }
}