package com.huishan.enjoytravel.ui.operation

import android.Manifest
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amap.api.location.*
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.model.*
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentSiteAddBinding
import com.huishan.enjoytravel.map.MapDrawUtil
import com.huishan.enjoytravel.map.MyLatLng
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.home.scanner.ScannerFragment
import com.huishan.enjoytravel.util.LogUtil
import com.huishan.enjoytravel.util.ScreenUtil
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * 新增站点
 * 后面应该根据具体接口实现，是通过本地定位，还是接口的给的经纬度来设置第一次的地图中心
 */
@AndroidEntryPoint
class SiteAddFragment : BaseFragment(), LocationSource, EasyPermissions.PermissionCallbacks,
    AMapLocationListener {

    companion object {
        const val TAG = "SiteAddFragment"
        const val REQUEST_CODE_QRCODE_PERMISSIONS: Int = 4
    }

    private var viewDataBinding: FragmentSiteAddBinding? = null
    private val viewModel by viewModels<SiteAddViewModel>()
    private var mLocationClient: AMapLocationClient? = null
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var aMap: AMap? = null
    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentSiteAddBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_site_add
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding!!.mapView.onResume()
        mLocationClient?.startLocation()
    }

    override fun onPause() {
        super.onPause()
        mLocationClient?.stopLocation()
        viewDataBinding!!.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mLocationClient?.onDestroy()
        viewDataBinding!!.mapView.onDestroy()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewDataBinding!!.mapView.onSaveInstanceState(outState)
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
        viewDataBinding!!.mapView.onCreate(savedInstanceState)
        requestLocatePermissions()
        initMap()
        viewDataBinding!!.btSave.setOnClickListener(onClickListener)
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "新增站点",
            null,
            R.drawable.ic_arrow_left,
            null,
            onClickListener
        )
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.iv_left -> {
                requireActivity().onBackPressed()
            }
            R.id.tv_title, R.id.iv_title_icon -> {

            }
            R.id.iv_right -> {

            }
        }
    }

    private fun initMap() {
        aMap = viewDataBinding!!.mapView.map
        aMap!!.uiSettings.isZoomControlsEnabled = false
        aMap!!.uiSettings.setLogoBottomMargin(ScreenUtil.dip2px(requireContext(), 20f))
        aMap!!.moveCamera(CameraUpdateFactory.zoomTo(18f))
        aMap!!.setLocationSource(this)
        aMap!!.isMyLocationEnabled = true
        aMap!!.myLocationStyle.showMyLocation(false)
        aMap?.setMyLocationType(AMap.LOCATION_TYPE_LOCATE)
        aMap!!.setOnMyLocationChangeListener {
            //navigation导航有时会导致此fragment进入onDestroy流程销毁，有时会导致地图位置不会更新到当前定位经纬度，这里在第一次得到定位时主动更新下地图位置
            if (isFirst) {
                isFirst = false
                aMap!!.moveCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition(
                            LatLng(
                                it.latitude,
                                it.longitude
                            ), 18f, 0f, 0f
                        )
                    )
                )
                /**这里要等mapView加载完才能初始化，不然会得到Point不准确*/
                viewDataBinding!!.mapView.postDelayed(Runnable { initDraw(aMap!!.myLocation.latitude, aMap!!.myLocation.longitude) }, 200)
            }

        }

    }

    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        mListener = p0
        if (mLocationClient == null) {
            mLocationClient = AMapLocationClient(requireContext().applicationContext)
            mLocationOption = AMapLocationClientOption()
            mLocationClient!!.setLocationListener(this)

            mLocationOption!!.locationMode =
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            mLocationClient!!.setLocationOption(mLocationOption)
            mLocationClient!!.startLocation()
        }
    }

    override fun deactivate() {
        mListener = null
        if (mLocationClient != null) {
            mLocationClient!!.stopLocation()
            mLocationClient!!.onDestroy()
        }
        mLocationClient = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onLocationChanged(p0: AMapLocation?) {
        if (mListener != null && p0 != null) {
            if (p0.errorCode == 0) {
                mListener!!.onLocationChanged(p0)
            } else {
                LogUtil.i(TAG, "定位失败:${p0.errorCode},${p0.errorInfo}")
            }
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private fun requestLocatePermissions() {
        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
        )
        if (!EasyPermissions.hasPermissions(requireContext(), *perms)) {
            EasyPermissions.requestPermissions(
                this,
                "地图需要定位的权限",
                ScannerFragment.REQUEST_CODE_QRCODE_PERMISSIONS,
                *perms
            )
        } else {

        }
    }

    /**
     * 仿照小安的贴片矩形选择区设置停车区，因为后端还没给接口，不知道要上传什么数据，不过大概率是把矩形Polygon的经纬度集合上传，SiteEditFragment应该也用这个方法
     */
    var targetVertex = -1
    //var mLatLngList: List<LatLng>? = null
    var mMyLatLng: List<MyLatLng>? = null
    var mPolygon: Polygon? = null
    var mMarkerList: ArrayList<Marker>? = null
    private fun initDraw(lat: Double, lng: Double) {
        val mLatLngList = MapDrawUtil.initRectangle(lat, lng)
        mMyLatLng = MapDrawUtil.convertToMyLatLngList(aMap!!.projection, mLatLngList)
        mPolygon = aMap!!.addPolygon(
            PolygonOptions().addAll(mLatLngList).fillColor(getColor(R.color.rectangle_fill_color))
                .strokeColor(getColor(R.color.rectangle_border_color)).strokeWidth(4f)
        )
        mMarkerList = MapDrawUtil.addRectangleMarker(requireContext().applicationContext, aMap!!, mLatLngList)
        aMap!!.setOnMapTouchListener { event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val downPoint = Point(event.x.toInt(), event.y.toInt())
                    targetVertex = MapDrawUtil.getVertex(mMyLatLng!!, downPoint)
                    if (targetVertex != -1) {
                        aMap!!.uiSettings.setAllGesturesEnabled(false)
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (targetVertex == -1) {
                        MapDrawUtil.updateRectangleByMove(
                            aMap!!.projection,
                            mMyLatLng!!,
                            mMarkerList!!,
                            mPolygon!!
                        )
                    } else {
                        MapDrawUtil.updateRectangleByDrag(
                            aMap!!.projection,
                            mMyLatLng!!,
                            mMarkerList!!,
                            mPolygon!!,
                            targetVertex,
                            Point(event.x.toInt(), event.y.toInt())
                        )
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (targetVertex == -1) {
                        MapDrawUtil.updateRectangleByMove(
                            aMap!!.projection,
                            mMyLatLng!!,
                            mMarkerList!!,
                            mPolygon!!
                        )
                    } else {
                        MapDrawUtil.updateRectangleByDrag(
                            aMap!!.projection,
                            mMyLatLng!!,
                            mMarkerList!!,
                            mPolygon!!,
                            targetVertex,
                            Point(event.x.toInt(), event.y.toInt())
                        )
                    }
                    aMap!!.uiSettings.setAllGesturesEnabled(true)
                }
            }
        }
    }
}