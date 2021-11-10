package com.huishan.enjoytravel.ui.operation

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentSiteEditBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.home.scanner.ScannerFragment
import com.huishan.enjoytravel.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * 添加站点
 * 具体实现参照SiteAddFragment
 */
@AndroidEntryPoint
class SiteEditFragment : BaseFragment(), LocationSource, EasyPermissions.PermissionCallbacks,
    AMapLocationListener {
    companion object {
        const val TAG = "SiteEditFragment"
        const val REQUEST_CODE_QRCODE_PERMISSIONS: Int = 5
    }

    private var viewDataBinding: FragmentSiteEditBinding? = null
    private val viewModel by viewModels<SiteEditViewModel>()
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
        viewDataBinding = FragmentSiteEditBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_site_edit
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
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "编辑站点",
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
        aMap!!.moveCamera(CameraUpdateFactory.zoomTo(17f))
        aMap!!.setLocationSource(this)
        aMap!!.isMyLocationEnabled = true
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
                            ), 17f, 0f, 0f
                        )
                    )
                )
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
}