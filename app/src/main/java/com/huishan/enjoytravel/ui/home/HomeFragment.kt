package com.huishan.enjoytravel.ui.home

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.huishan.enjoytravel.BikeApplication
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.request.GetCurrentServiceRequest
import com.huishan.enjoytravel.databinding.FragmentHomeBinding
import com.huishan.enjoytravel.ui.FragmentBackStatus
import com.huishan.enjoytravel.ui.home.scanner.ScannerFragment
import com.huishan.enjoytravel.util.LogUtil
import com.huishan.enjoytravel.util.SignUtils
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**首页*/
@AndroidEntryPoint
class HomeFragment : BaseFragment(), LocationSource, EasyPermissions.PermissionCallbacks, AMapLocationListener {

    companion object {
        const val TAG = "HomeFragment"
        const val REQUEST_CODE_QRCODE_PERMISSIONS: Int = 1
    }

    private val viewModel by activityViewModels<HomeViewModel>()
    private var viewDataBinding: FragmentHomeBinding? = null

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
        viewDataBinding = FragmentHomeBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun initData(view: View,savedInstanceState: Bundle?) {
        initTitleBar()
        viewDataBinding!!.mapView.onCreate(savedInstanceState)
        requestLocatePermissions()
        initMap()
        initLiveDataObserve()
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
        aMap = null
        viewDataBinding?.mapView?.removeAllViews()
        viewDataBinding?.mapView?.onDestroy()
        viewDataBinding?.unbind()
        viewDataBinding = null

    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e(TAG, "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewDataBinding!!.mapView.onSaveInstanceState(outState)
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "我享出行深圳服务区",
            R.drawable.ic_black_triangle_down,
            R.drawable.ic_vehicle_list,
            R.drawable.ic_filter_condition,
            onClickListener
        )
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.iv_left -> {
                it.findNavController().navigate(R.id.bike_list_fragment_dest)
            }
            R.id.tv_title, R.id.iv_title_icon -> {
                findNavController().navigate(R.id.service_select_fragment_dest)
            }
            R.id.iv_right -> {
                showPopWindow()
            }
        }
    }

    private fun initLiveDataObserve() {
        viewModel.progressVisible.observe(this) {
            if (it) {
                viewDataBinding!!.batterySeekbar.visibility = View.VISIBLE
            } else {
                viewDataBinding!!.batterySeekbar.visibility = View.GONE
            }
        }
        viewModel.mapType.observe(this) {
            if (it) {
                aMap?.mapType = AMap.MAP_TYPE_SATELLITE
            } else {
                aMap?.mapType = AMap.MAP_TYPE_NORMAL
            }
        }
        viewModel.carCapacityVisible.observe(this) {
            if (it) {
                viewDataBinding!!.bikeBox.visibility = View.VISIBLE
            } else {
                viewDataBinding!!.bikeBox.visibility = View.GONE
            }
        }
        viewModel.locationReset.observe(this) {
            aMap?.myLocation?.let {
                aMap?.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(it.latitude, it.longitude), viewModel.zoomIndex, 0f, 0f)))
                //aMap.addMarker(MapDrawUtil.drawMarker(requireContext().applicationContext, "单车", it.latitude, it.longitude, MapDrawUtil.BIKE_TYPE_HIGH_POWER))
            }
        }
        //根据经纬度获取当前定位区域
        viewModel.currentService.observe(this) { entity ->
            entity?.let {
                setTitleText(viewDataBinding!!.root, it.address)
            }
        }
    }

    private fun initMap() {

        aMap = viewDataBinding!!.mapView.map
        aMap?.uiSettings?.isZoomControlsEnabled = false
        aMap?.moveCamera(CameraUpdateFactory.zoomTo(viewModel.zoomIndex))
        aMap?.setLocationSource(this)
        aMap?.isMyLocationEnabled = true
        aMap?.setMyLocationType(AMap.LOCATION_TYPE_LOCATE)

        aMap?.setOnMyLocationChangeListener {
            //navigation导航有时会导致此fragment进入onDestroy流程销毁，有时会导致地图位置不会更新到当前定位经纬度，这里在第一次得到定位时主动更新下地图位置
            if (isFirst) {
                isFirst = false
                aMap?.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(it.latitude, it.longitude), viewModel.zoomIndex, 0f, 0f)))
            }
            //app第一次进入首页时才根据经纬度获取本地服务区，其他时候应使用已选择的服务区
            if (viewModel.isFirst) {
                viewModel.isFirst = false
                viewModel.getCurrentService(SignUtils.getSignMap(GetCurrentServiceRequest(it.latitude, it.longitude)))
            }
        }
    }

    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        mListener = p0
        if (mLocationClient == null) {
            mLocationClient = AMapLocationClient(requireContext().applicationContext)
            mLocationOption = AMapLocationClientOption()
            mLocationClient!!.setLocationListener(this)

            mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
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

    override fun onLocationChanged(p0: AMapLocation?) {
        if (mListener != null && p0 != null) {
            if (p0.errorCode == 0) {
                mListener!!.onLocationChanged(p0)
            } else {
                Log.i(TAG, "定位失败:${p0.errorCode},${p0.errorInfo}")
            }
        }
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
                REQUEST_CODE_QRCODE_PERMISSIONS,
                *perms
            )
        } else {

        }
    }

    var dialog: BikeStatusDialogFragment? = null
    private fun showPopWindow() {
        if (dialog == null) {
            dialog = BikeStatusDialogFragment()
        }
        dialog!!.show(childFragmentManager, "bikeStatus")
    }

    override fun setCurrentFragmentBackStatus() {
        BikeApplication.fragmentBackStatus = FragmentBackStatus.EXIT_APP
    }

}