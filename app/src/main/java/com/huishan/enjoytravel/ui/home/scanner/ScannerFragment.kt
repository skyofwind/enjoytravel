package com.huishan.enjoytravel.ui.home.scanner

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.clj.fastble.BleManager
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.bluetooth.BluetoothConstant
import com.huishan.enjoytravel.databinding.FragmentScannerBinding
import com.huishan.enjoytravel.ui.BlueToothViewModel
import com.huishan.enjoytravel.ui.DataShareViewModel
import com.huishan.enjoytravel.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**扫一扫*/
@AndroidEntryPoint
class ScannerFragment : BaseFragment(), EasyPermissions.PermissionCallbacks, QRCodeView.Delegate {
    companion object {
        const val TAG = "ScannerFragment"
        const val REQUEST_CODE_QRCODE_PERMISSIONS: Int = 2
        const val REQUEST_CODE_BLUETOOTH_PERMISSIONS: Int = 12
    }

    private val viewModel by viewModels<ScannerViewModel>()
    private val blueToothViewModel by activityViewModels<BlueToothViewModel>()
    private val dataShareViewModel by activityViewModels<DataShareViewModel>()
    private var viewDataBinding: FragmentScannerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentScannerBinding.bind(view).apply {
            viewmodel = viewModel
            bluetoothviewmodel = blueToothViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_scanner
    }

    override fun onStart() {
        super.onStart()
        startCamera()
    }

    override fun onStop() {
        // 关闭摄像头预览，并且隐藏扫描框
        viewDataBinding!!.zxingview.stopCamera()
        viewDataBinding!!.cbFlash.isChecked = false
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 销毁二维码扫描控件
        viewDataBinding!!.zxingview.onDestroy()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        viewDataBinding!!.zxingview.setDelegate(this)
        requestCodeQRCodePermissions()
        /**这里是蓝牙测试，感觉实际无网情况下通过9位数车辆编号直连电动车蓝牙不现实，因为不能通过车辆编号得到MAC地址*/
        //requestCodeBlueToothPermissions()
        initLiveDataObserve()
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
        LogUtil.e(TAG, "permission granted $requestCode")
        if (requestCode == REQUEST_CODE_QRCODE_PERMISSIONS) {
            startCamera()
        }
        if (requestCode == REQUEST_CODE_BLUETOOTH_PERMISSIONS) {
            //connectBlueTooth(BlueToothUtil.convertImeiToAddr(BluetoothConstant.MAC))
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        LogUtil.e(TAG, "permission denied $requestCode")
    }

    @SuppressLint("LogUtilNotTimber")
    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private fun requestCodeQRCodePermissions() {
        val perms = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (!EasyPermissions.hasPermissions(requireContext(), *perms)) {
            LogUtil.i(TAG, "requestCodeQRCodePermissions 1")
            EasyPermissions.requestPermissions(
                this,
                "扫描二维码需要打开相机和散光灯的权限",
                REQUEST_CODE_QRCODE_PERMISSIONS,
                *perms
            )
        } else {
            LogUtil.i(TAG, "requestCodeQRCodePermissions 2")
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
                //blueToothViewModel.connectBlueTooth(BlueToothUtil.convertImeiToAddr(BluetoothConstant.MAC), false)
            } else {
                BleManager.getInstance().enableBluetooth()
            }
        }
    }


    @SuppressLint("LogUtilNotTimber")
    fun startCamera() {
        LogUtil.i(TAG, "startCamera")
        // 打开后置摄像头开始预览，但是并未开始识别
        viewDataBinding!!.zxingview.startCamera()
        //mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        viewDataBinding!!.zxingview.startSpotAndShowRect()
    }

    /**扫描结果*/
    @SuppressLint("LogUtilNotTimber")
    override fun onScanQRCodeSuccess(result: String?) {
        LogUtil.i(TAG, "result:$result")
        lifecycleScope.launch(Dispatchers.Main) {
            vibrate()
            result?.let { viewModel.setBikeNumber(it) }
            delay(500)
            viewDataBinding!!.zxingview.startSpot()
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        var tipText: String = viewDataBinding!!.zxingview.scanBoxView.tipText
        val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                viewDataBinding!!.zxingview.scanBoxView.tipText = tipText + ambientBrightnessTip
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                viewDataBinding!!.zxingview.scanBoxView.tipText = tipText
            }
        }
    }

    override fun onScanQRCodeOpenCameraError() {
        LogUtil.e(TAG, "打开相机出错")
    }

    /**注意，还不知道到底要直连蓝牙还是用接口控制，这里的逻辑后面应该是要大幅变动的*/
    private fun initLiveDataObserve() {
        /**设置此页面bikeNumber的值*/
        if (dataShareViewModel.getBikeNumber().isNotEmpty()) {
            viewModel.setBikeNumber(dataShareViewModel.getBikeNumber())
            dataShareViewModel.resetBikeNumber("")
        }
        viewModel.flashOpen.observe(this) {
            if (it) {
                viewDataBinding!!.zxingview.openFlashlight()
            } else {
                viewDataBinding!!.zxingview.closeFlashlight()
            }
        }
        /**bikeNumber发生变化则执行writeSomething*/
        viewModel.bikeNumber.observe(this) {
            LogUtil.e("bikeNumberGet", "$it")
            if (!it.isNullOrEmpty()) {
                viewModel.writeSomething(viewDataBinding!!.root, viewModel.scanType.value, blueToothViewModel)
            }
        }
        blueToothViewModel.failure.observe(this) {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        /**蓝牙连接情况通知*/
        blueToothViewModel.isConnect.observe(this) {
            LogUtil.e("isConnect receive", "${it.name}, ${it.isConnect}, ${it.isReconnect}, - ${it}")
            it?.let { bleConnectModel ->
                if (bleConnectModel.isConnect) {
                    viewDataBinding!!.tvConnect.text = getString(R.string.value_tip_connect_success, bleConnectModel.name.toString())
                    //首次连接成功时执行相应已选按钮的蓝牙操作
                    if (!bleConnectModel.isReconnect) {
                        /**注意，这里执行的操作可能会跟viewModel.bikeNumber.observe中的会重复，导致多次调用viewModel.writeSomething*/
                        //viewModel.writeSomething(viewDataBinding!!.root, viewModel.scanType.value, blueToothViewModel)
                    }
                } else {
                    if (bleConnectModel.isReconnect) {
                        viewDataBinding!!.tvConnect.text = getString(R.string.value_tip_reconnect_fail, bleConnectModel.name.toString())
                    } else {
                        viewDataBinding!!.tvConnect.text = getString(R.string.value_tip_connect_fail, bleConnectModel.name.toString())
                    }
                }
            }
        }
        blueToothViewModel.unlockRearWheel.observe(this) {
            it?.let { bleResponse ->
                Toast.makeText(requireContext(), bleResponse.msg, Toast.LENGTH_SHORT).show()
            }
        }
        blueToothViewModel.lockRearWheel.observe(this) {
            it?.let { bleResponse ->
                Toast.makeText(requireContext(), bleResponse.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun vibrate() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

}