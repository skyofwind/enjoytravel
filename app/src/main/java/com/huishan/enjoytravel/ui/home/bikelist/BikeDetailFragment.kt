package com.huishan.enjoytravel.ui.home.bikelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.clj.fastble.BleManager
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.bluetooth.BlueToothUtil
import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.databinding.FragmentBikeDetailBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.BlueToothViewModel
import com.huishan.enjoytravel.ui.home.scanner.ScannerFragment
import com.huishan.enjoytravel.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**车辆详情*/
@AndroidEntryPoint
class BikeDetailFragment: BaseFragment(), EasyPermissions.PermissionCallbacks {
    
    companion object {
        const val TAG = "BikeDetailFragment"
        const val REQUEST_CODE_BLUETOOTH_PERMISSIONS: Int = 22
    }

    private var viewDataBinding: FragmentBikeDetailBinding? = null
    private val viewModel by viewModels<BikeDetailViewModel>()
    private val blueToothViewModel by activityViewModels<BlueToothViewModel>()

    private var aMap: AMap? = null
    private val zoomIndex = 17f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentBikeDetailBinding.bind(view).apply {
            viewmodel = viewModel
            bluetoothviewmodel = blueToothViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_bike_detail
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding!!.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewDataBinding!!.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        aMap = null
        viewDataBinding?.mapView?.removeAllViews()
        viewDataBinding?.mapView?.onDestroy()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewDataBinding!!.mapView.onSaveInstanceState(outState)
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
        requestCodeBlueToothPermissions()
        viewDataBinding!!.mapView.onCreate(savedInstanceState)
        initMap()
        initLiveDataObserve()
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "车辆详情",
            null,
            R.drawable.ic_arrow_left,
            R.drawable.ic_detail,
            onClickListener
        )
    }

    private fun initMap() {
        aMap = viewDataBinding!!.mapView.map
        aMap?.uiSettings?.isZoomControlsEnabled = false
        aMap?.moveCamera(CameraUpdateFactory.zoomTo(zoomIndex))
        aMap?.isMyLocationEnabled = true
        aMap?.setMyLocationType(AMap.LOCATION_TYPE_LOCATE)
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.iv_left -> {
                requireActivity().onBackPressed()
            }
            /*R.id.tv_title, R.id.iv_title_icon -> {
            }*/
             R.id.iv_right -> {

             }
        }
    }

    @AfterPermissionGranted(REQUEST_CODE_BLUETOOTH_PERMISSIONS)
    private fun requestCodeBlueToothPermissions() {
        if (!EasyPermissions.hasPermissions(requireContext(), *BluetoothConstant.perms)) {
            LogUtil.i(TAG, "requestCodeBlueToothPermissions 1")
            EasyPermissions.requestPermissions(
                this,
                "控制电动车需要蓝牙权限",
                REQUEST_CODE_BLUETOOTH_PERMISSIONS,
                *BluetoothConstant.perms
            )
        } else {
            LogUtil.i(TAG, "requestCodeBlueToothPermissions 2")
            if (BleManager.getInstance().isBlueEnable) {
                blueToothViewModel.connectBlueTooth(
                    BlueToothUtil.convertImeiToAddr(
                        BluetoothConstant.MAC), false)
            } else {
                BleManager.getInstance().enableBluetooth()
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
        LogUtil.e(ScannerFragment.TAG, "permission granted $requestCode")

        if (requestCode == ScannerFragment.REQUEST_CODE_BLUETOOTH_PERMISSIONS) {
            //blueToothViewModel.connectBlueTooth(BlueToothUtil.convertImeiToAddr(BluetoothConstant.MAC), false)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        LogUtil.e(ScannerFragment.TAG, "permission denied $requestCode")
    }

    private fun initLiveDataObserve() {
        viewModel.failure.observe(this) { msg ->
            msg?.let { Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show() }
        }
        blueToothViewModel.failure.observe(this) { msg ->
            msg?.let { Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show() }
        }
        blueToothViewModel.unlockRearWheel.observe(this) { res ->
            res?.let { Toast.makeText(requireContext(), res.msg, Toast.LENGTH_SHORT).show() }
        }
        blueToothViewModel.lockRearWheel.observe(this) { res ->
            res?.let { Toast.makeText(requireContext(), res.msg, Toast.LENGTH_SHORT).show() }
        }
        blueToothViewModel.playVoice.observe(this) { res ->
            res?.let { Toast.makeText(requireContext(), res.msg, Toast.LENGTH_SHORT).show() }
        }
    }
}