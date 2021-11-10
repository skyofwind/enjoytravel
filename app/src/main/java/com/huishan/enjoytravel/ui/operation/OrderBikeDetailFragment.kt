package com.huishan.enjoytravel.ui.operation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.databinding.FragmentOrderBikeDetailBinding
import com.huishan.enjoytravel.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * 车辆详情
 */
@AndroidEntryPoint
class OrderBikeDetailFragment: BaseFragment() {

    private var viewDataBinding: FragmentOrderBikeDetailBinding? = null
    private val viewModel by viewModels<OrderBikeDetailViewModel>()

    private var aMap: AMap? = null
    private val zoomIndex = 17f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentOrderBikeDetailBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_order_bike_detail
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
        viewDataBinding!!.mapView.onCreate(savedInstanceState)
        initMap()
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "车辆详情",
            null,
            R.drawable.ic_arrow_left,
            null,
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