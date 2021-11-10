package com.huishan.enjoytravel.ui.maintenance

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemOnAndOffShelvesEntity
import com.huishan.enjoytravel.databinding.FragmentOnAndOffShelvesBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.MyBindingAdapter
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**上下架*/
@AndroidEntryPoint
class OnAndOffShelvesFragment: BaseFragment(), EasyPermissions.PermissionCallbacks, QRCodeView.Delegate {

    companion object {
        const val TAG = "OnAndOffShelvesFragment"
        const val REQUEST_CODE_QRCODE_PERMISSIONS: Int = 3
    }

    private var viewDataBinding: FragmentOnAndOffShelvesBinding? = null
    private val viewModel by viewModels<OnAndOffShelvesViewModel>()
    var adapter: MyBindingAdapter<ItemOnAndOffShelvesEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentOnAndOffShelvesBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_on_and_off_shelves
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
        initTitleBar()
        initList()
        viewDataBinding!!.zxingview.setDelegate(this)
        requestCodeQRCodePermissions()
        initLiveDataObserve()
    }

    fun initList() {
        if (adapter == null) {
            adapter = MyBindingAdapter(requireContext(), R.layout.item_on_and_off_shelves, viewModel, viewModel.list)
            viewDataBinding!!.rvList.layoutManager = LinearLayoutManager(requireContext())
            //viewDataBinding!!.rvList.addItemDecoration(SpacesItemDecoration(0, 40, 0, 0))
            viewDataBinding!!.rvList.adapter = adapter
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
        if (requestCode == REQUEST_CODE_QRCODE_PERMISSIONS) {
            startCamera()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    @SuppressLint("LogNotTimber")
    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private fun requestCodeQRCodePermissions() {
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!EasyPermissions.hasPermissions(requireContext(), *perms)) {
            Log.i(TAG, "requestCodeQRCodePermissions 1")
            EasyPermissions.requestPermissions(
                this,
                "扫描二维码需要打开相机和散光灯的权限",
                REQUEST_CODE_QRCODE_PERMISSIONS,
                *perms
            )
        } else {
            Log.i(TAG, "requestCodeQRCodePermissions 2")
        }
    }

    @SuppressLint("LogNotTimber")
    fun startCamera() {
        Log.i(TAG, "startCamera")
        // 打开后置摄像头开始预览，但是并未开始识别
        viewDataBinding!!.zxingview.startCamera()
        //mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        viewDataBinding!!.zxingview.startSpotAndShowRect()
    }

    /**扫描结果*/
    @SuppressLint("LogNotTimber")
    override fun onScanQRCodeSuccess(result: String?) {
        Log.i(TAG, "result:$result")
        vibrate()
        //Thread.sleep(500)
        //viewDataBinding!!.zxingview.startSpot()
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
        Log.e(TAG, "打开相机出错")
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

    private fun vibrate() {
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "不默认选中",
            R.drawable.ic_black_triangle_down,
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
                findNavController().navigate(R.id.service_select_fragment_dest)
            }
            R.id.iv_right -> {

            }
        }
    }

}