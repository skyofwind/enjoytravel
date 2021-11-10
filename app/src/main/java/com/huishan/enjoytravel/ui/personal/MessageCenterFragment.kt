package com.huishan.enjoytravel.ui.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.entity.ItemMessageEntity
import com.huishan.enjoytravel.databinding.FragmentMessageCenterBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.MyBindingAdapter
import com.huishan.enjoytravel.widget.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**消息中心*/
@AndroidEntryPoint
class MessageCenterFragment: BaseFragment() {

    private var viewDataBinding: FragmentMessageCenterBinding? = null
    private val viewModel by viewModels<MessageCenterViewModel>()
    var adapter: MyBindingAdapter<ItemMessageEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentMessageCenterBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return view
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_message_center
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initTitleBar()
    }

    private fun initTitleBar() {
        setDefaultTitle(
            viewDataBinding!!.root,
            "消息中心",
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

    fun initList() {
        if (adapter == null) {
            adapter = MyBindingAdapter(requireContext(), R.layout.item_message_center, viewModel, viewModel.entityList)
            viewDataBinding!!.rvList.layoutManager = LinearLayoutManager(requireContext())
            viewDataBinding!!.rvList.addItemDecoration(SpacesItemDecoration(0, 40, 0, 0))
            viewDataBinding!!.rvList.adapter = adapter
        }
    }
}