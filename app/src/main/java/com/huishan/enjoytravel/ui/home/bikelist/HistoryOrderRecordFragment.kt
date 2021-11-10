package com.huishan.enjoytravel.ui.home.bikelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.HistoryRecordEntity
import com.huishan.enjoytravel.data.remote.request.GetCurrentServiceRequest
import com.huishan.enjoytravel.databinding.FragmentHistoryOrderRecordBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.MyBindingAdapter
import com.huishan.enjoytravel.util.SignUtils
import com.huishan.enjoytravel.widget.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**历史订单记录*/
@AndroidEntryPoint
class HistoryOrderRecordFragment : BaseFragment() {

    private var viewDataBinding: FragmentHistoryOrderRecordBinding? = null
    private val viewModel by viewModels<HistoryOrderRecordViewModel>()
    var adapter: MyBindingAdapter<HistoryRecordEntity>? = null
    private var aMap: AMap? = null
    private val zoomIndex = 17f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentHistoryOrderRecordBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_history_order_record
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
        initList()
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "我享出行深圳服务区",
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
            /* R.id.iv_right -> {

             }*/
        }
    }

    fun initList() {
        if (adapter == null) {
            adapter = MyBindingAdapter(requireContext(), R.layout.item_history_order_record, viewModel, viewModel.list)
            viewDataBinding!!.rvList.layoutManager = LinearLayoutManager(requireContext())
            viewDataBinding!!.rvList.addItemDecoration(SpacesItemDecoration(0, 40, 0, 0))
            viewDataBinding!!.rvList.adapter = adapter
        }
    }

    private fun initMap() {
        aMap = viewDataBinding!!.mapView.map
        aMap?.uiSettings?.isZoomControlsEnabled = false
        aMap?.moveCamera(CameraUpdateFactory.zoomTo(zoomIndex))
        aMap?.isMyLocationEnabled = true
        aMap?.setMyLocationType(AMap.LOCATION_TYPE_LOCATE)
    }
}