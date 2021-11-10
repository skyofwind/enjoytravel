package com.huishan.enjoytravel.map

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.amap.api.location.AMapLocationClient
import com.amap.api.maps.MapView
import java.lang.ref.SoftReference

class MapListener(private val mapView: SoftReference<MapView>, var locationClient: SoftReference<AMapLocationClient>, var bundle: Bundle?) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.i("MapListener", "onCreate")
        mapView.get()?.onCreate(bundle)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.i("MapListener", "onResume")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.i("MapListener", "onPause")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.i("MapListener", "onDestroy")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.i("MapListener", "onStart")
        if(locationClient.get() == null) {
            Log.i("MapListener", "onStart locationClient == null")
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.i("MapListener", "onStop")
        if(locationClient.get() == null) {
            Log.i("MapListener", "onStop locationClient == null")
        }

    }

    /**Lifecycle生命周期没这个，不知道怎么解决，先自己调用吧*/
    fun onSaveInstanceState(outState: Bundle?) {
        Log.i("MapListener", "onSaveInstanceState")
        bundle = outState
        mapView.get()?.onSaveInstanceState(bundle)
    }
}