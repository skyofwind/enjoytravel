package com.huishan.enjoytravel.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.amap.api.maps.MapFragment
import com.google.android.material.snackbar.Snackbar
import com.huishan.enjoytravel.BikeApplication
import com.huishan.enjoytravel.R
import com.huishan.enjoytravel.data.remote.HttpConstants
import com.huishan.enjoytravel.databinding.FragmentLoginBinding
import com.huishan.enjoytravel.ui.BaseFragment
import com.huishan.enjoytravel.ui.FragmentBackStatus
import com.huishan.enjoytravel.util.setupSnackbar
import dagger.hilt.android.AndroidEntryPoint

/**登录*/
@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private var viewDataBinding: FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResId(), container, false)
        viewDataBinding = FragmentLoginBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return view
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_login
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.unbind()
        viewDataBinding = null
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        setupSnackbar()
        initObserver()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    override fun setCurrentFragmentBackStatus() {
        BikeApplication.fragmentBackStatus = FragmentBackStatus.EXIT_APP
    }

    private fun initObserver() {
        viewModel.failure.observe(this) { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
        viewModel.login.observe(this) { entity ->
            entity?.let {
                HttpConstants.Header.token = it.accessToken.toString()
                viewDataBinding!!.root.findNavController().navigate(R.id.home_fragment_dest)
            }
        }
    }
}