package com.huishan.enjoytravel.map

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.huishan.enjoytravel.R

import android.graphics.Bitmap
import android.graphics.Point
import com.amap.api.location.DPoint
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.Projection
import com.amap.api.maps.model.*
import com.huishan.enjoytravel.util.LogUtil
import com.huishan.enjoytravel.util.ScreenUtil
import java.math.BigDecimal
import kotlin.math.abs


class MapDrawUtil {
    companion object {
        const val BIKE_TYPE_HIGH_POWER = R.drawable.bike_type_high_power
        const val BIKE_TYPE_MEDIUM_POWER = R.drawable.bike_medium_power
        const val BIKE_TYPE_LOW_POWER = R.drawable.bike_type_low_power
        const val BIKE_TYPE_NO_POWER = R.drawable.bike_type_no_power
        const val BIKE_TYPE_TYPE_WARN = R.drawable.bike_type_warn
        const val BIKE_TYPE_OFF_SITE = R.drawable.bike_type_off_site
        const val MARKER_CIRCLE = R.drawable.ic_marker_circle

        const val MARKER_CIRCLE_DIAMETER = 25f
        private var markerCircleRadius = 0

        fun drawMarker(
            context: Context,
            title: String?,
            latitude: Double,
            longitude: Double,
            @DrawableRes ids: Int
        ): MarkerOptions {
            val markerOptions = MarkerOptions()
            markerOptions.position(LatLng(latitude, longitude))
                .title(if (title.isNullOrEmpty()) "" else title)
                .draggable(false)
                .icon(
                    //通过zoomImage设置图片大小
                    BitmapDescriptorFactory.fromBitmap(
                        zoomImage(
                            context,
                            ids,
                            ScreenUtil.dip2px(context, 36f),
                            ScreenUtil.dip2px(context, 25.5f)
                        )
                    )
                )
                .setFlat(false)

            return markerOptions
        }

        /**
         * 将本地资源图片大小缩放
         * @param resId
         * @param w
         * @param h
         * @return
         */
        fun zoomImage(context: Context, resId: Int, w: Int, h: Int): Bitmap? {
            LogUtil.e("zoomImage", "width = $w, height = $h")
            val res: Resources = context.resources
            val oldBmp = BitmapFactory.decodeResource(res, resId)
            return Bitmap.createScaledBitmap(oldBmp, w, h, true)
        }

        /**
         * 参照 https://www.jianshu.com/p/e0de5d416c89 实现
         * 多边形实现选区，目前是矩形，自己根据根据需求进行更改，我们没有需求说明文档(拉胯)
         */
        fun initRectangle(latitude: Double, longitude: Double): List<LatLng> {
            val list = ArrayList<LatLng>(4)
            val centerLat = BigDecimal(latitude)
            val centerLong = BigDecimal(longitude)
            val hOffset = BigDecimal("0.0002")
            val vOffset = BigDecimal("0.0003")
            //左下
            var latlng = LatLng(
                centerLat.subtract(hOffset).toDouble(),
                centerLong.subtract(vOffset).toDouble()
            )
            list.add(latlng)
            //右下
            latlng =
                LatLng(centerLat.subtract(hOffset).toDouble(), centerLong.add(vOffset).toDouble())
            list.add(latlng)
            //右上
            latlng = LatLng(centerLat.add(hOffset).toDouble(), centerLong.add(vOffset).toDouble())
            list.add(latlng)
            //左上
            latlng =
                LatLng(centerLat.add(hOffset).toDouble(), centerLong.subtract(vOffset).toDouble())
            list.add(latlng)

            return list
        }

        fun addRectangleMarker(
            context: Context,
            aMap: AMap,
            list: List<LatLng>
        ): ArrayList<Marker> {
            val mList = ArrayList<Marker>(4)
            for (i in list.indices) {
                val diameter = ScreenUtil.dip2px(context, MARKER_CIRCLE_DIAMETER)
                markerCircleRadius = diameter / 2
                val options = MarkerOptions()
                options.position(list[i]).draggable(false).visible(true).anchor(0.5f, 0.5f).icon(
                    BitmapDescriptorFactory.fromBitmap(
                        zoomImage(
                            context,
                            MARKER_CIRCLE,
                            diameter,
                            diameter
                        )
                    )
                )

                val marker = aMap.addMarker(options)
                marker.`object` = i
                mList.add(marker)
            }

            return mList
        }

        fun convertToLatLngList(list: List<MyLatLng>): List<LatLng> {
            val mList = ArrayList<LatLng>(list.size)
            for (i in list) {
                mList.add(i.latlng)
            }

            return mList
        }

        fun convertToMyLatLngList(projection: Projection, list: List<LatLng>): List<MyLatLng> {
            val mList = ArrayList<MyLatLng>(list.size)
            for (i in list) {
                mList.add(MyLatLng(i, projection.toScreenLocation(i)))
            }

            return mList
        }

        /**地图移动刷新贴片矩形的位置,因为需要移动屏幕，但不移动贴片图形，所以需要根据记录的Point，去修改LatLng*/
        fun updateRectangleByMove(
            projection: Projection,
            mList: List<MyLatLng>,
            markerList: ArrayList<Marker>,
            polygan: Polygon
        ) {

            val list = ArrayList<LatLng>(mList.size)
            for (i in markerList.indices) {
                mList[i].latlng = projection.fromScreenLocation(mList[i].point)
                markerList[i].position = mList[i].latlng
                list.add(mList[i].latlng)
            }
            polygan.points = list

        }

        /**
         * 之前添加marker时设置了锚点（0.5， 0.5），即按照原本的坐标点向左和向下偏移了markerCircleRadius位移
         * 因此只要通过point判断是否处于marker范围即可得到点击的是哪个marker
         */
        fun getVertex(list: List<MyLatLng>, point: Point): Int {
            for (i in list.indices) {
                val x = abs(list[i].point.x - point.x)
                val y = abs(list[i].point.y - point.y)
                if (x <= markerCircleRadius && y <= markerCircleRadius) {
                    LogUtil.e("getNearby", "i $i")
                    return i
                }
            }

            return -1
        }

        /**按住marker移动时，对marker进行移动处理，并更新矩形的形状*/
        fun updateRectangleByDrag(
            projection: Projection,
            mList: List<MyLatLng>,
            markerList: ArrayList<Marker>,
            polygan: Polygon,
            position: Int,
            point: Point
        ) {
            mList[position].point = point
            mList[position].latlng = projection.fromScreenLocation(point)
            markerList[position].position = mList[position].latlng
            when(position) {
                //左下，x变动时，左上的x也要变动，y变动时，右下的y也要变动
                0 -> {
                    dragDrawMarker(mList[position], mList[3], markerList[3], true, projection)
                    dragDrawMarker(mList[position], mList[1], markerList[1], false, projection)
                }
                //右下，x变动时，右上的x也要变动，y变动时，左下的y也要变动
                1 -> {
                    dragDrawMarker(mList[position], mList[2], markerList[2], true, projection)
                    dragDrawMarker(mList[position], mList[0], markerList[0], false, projection)
                }
                //右上，x变动时，右下的x也要变动，y变动时，左上的y也要变动
                2 -> {
                    dragDrawMarker(mList[position], mList[1], markerList[1], true, projection)
                    dragDrawMarker(mList[position], mList[3], markerList[3], false, projection)
                }
                //左上，x变动时左下的x也要变动，y变动时，右上的y也要变动
                3 -> {
                    dragDrawMarker(mList[position], mList[0], markerList[0], true, projection)
                    dragDrawMarker(mList[position], mList[2], markerList[2], false, projection)
                }
            }
            polygan.points = convertToLatLngList(mList)
        }

        private fun dragDrawMarker(dest: MyLatLng, target: MyLatLng, marker: Marker, isX: Boolean, projection: Projection) {
            if (isX) {
                if (target.point.x == dest.point.x) {
                    return
                }
                target.point.x = dest.point.x
            } else {
                if (target.point.y == dest.point.y) {
                    return
                }
                target.point.y = dest.point.y
            }
            target.latlng = projection.fromScreenLocation(target.point)
            marker.position = target.latlng
        }
    }
}