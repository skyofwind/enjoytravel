package com.huishan.enjoytravel.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentCommonScannerBinding
import com.huishan.enjoytravel.util.LogUtil
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * 通用扫码
 * 扫描，通过dataShareViewModel保存车辆编号
 */
@AndroidEntryPoint
class CommonScannerFragment : BaseFragment(), EasyPermissions.PermissionCallbacks,
    QRCodeView.Delegate {
    companion object {
        const val TAG = "CommonScannerFragment"
        const val REQUEST_CODE_QRCODE_PERMISSIONS: Int = 23

        const val SCAN_TAG = "scan"
        const val SCAN_TYPE_BIKE_NUMBER = 1
        const val SCAN_TYPE_BOX_NUMBER = 2
    }

    private val viewModel by viewModels<CommonScannerViewModel>()
    private val dataShareViewModel by activityViewModels<DataShareViewModel>()
    private var viewDataBinding: FragmentCommonScannerBinding? = null
    private var type = SCAN_TYPE_BIKE_NUMBER

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentCommonScannerBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_common_scanner
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            type = it.getInt(SCAN_TAG, SCAN_TYPE_BIKE_NUMBER)
        }

        viewDataBinding!!.zxingview.setDelegate(this)
        requestCodeQRCodePermissions()
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
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        LogUtil.e(TAG, "permission denied $requestCode")
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
        when(type) {
            SCAN_TYPE_BIKE_NUMBER -> {dataShareViewModel.resetBikeNumber(result.toString())}
            SCAN_TYPE_BOX_NUMBER -> {dataShareViewModel.resetBoxNumber(result.toString())}
        }

        requireActivity().onBackPressed()
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

    private fun initLiveDataObserve() {
        viewModel.flashOpen.observe(this) {
            if (it) {
                viewDataBinding!!.zxingview.openFlashlight()
            } else {
                viewDataBinding!!.zxingview.closeFlashlight()
            }
        }
    }
}