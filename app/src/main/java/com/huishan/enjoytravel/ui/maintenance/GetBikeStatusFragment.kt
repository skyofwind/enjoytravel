package com.huishan.enjoytravel.ui.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentGetBikeStatusBinding
import com.huishan.enjoytravel.databinding.FragmentVehicleBoxBindingBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.DataShareViewModel
import dagger.hilt.android.AndroidEntryPoint

/**获取车辆状态*/
@AndroidEntryPoint
class GetBikeStatusFragment: BaseFragment() {

    private var viewDataBinding: FragmentGetBikeStatusBinding? = null
    private val viewModel by viewModels<GetBikeStatusViewModel>()
    private val dataShareViewModel by activityViewModels<DataShareViewModel>()

    private var aMap: AMap? = null
    private val zoomIndex = 17f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentGetBikeStatusBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_get_bike_status
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
        if (dataShareViewModel.getBikeNumber().isNotEmpty()) {
            viewDataBinding!!.etBikeNumber.setText(dataShareViewModel.getBikeNumber())
            dataShareViewModel.resetBikeNumber("")
        }
        viewDataBinding!!.mapView.onCreate(savedInstanceState)
        initMap()
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "获取车辆状态",
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
            R.id.tv_title, R.id.iv_title_icon -> {

            }
            R.id.iv_right -> {

            }
        }
    }
}