package com.huishan.enjoytravel.map

import android.graphics.Point
import com.amap.api.maps.model.LatLng

class MyLatLng(var latlng: LatLng, var point: Point) {

    override fun toString(): String {
        return "latlng = ${latlng.toString()}, point = ${point.toString()}"
    }
}