package com.malakapps.fxrate.baseAndroid

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.malakapps.fxrate.baseAndroid.view.spinner.LoadingSpinner

abstract class BaseFragment<VM: BaseViewModel>(@LayoutRes layoutResourceId: Int) : Fragment(layoutResourceId) {
    abstract val viewModel: VM

    private var loadingSpinner: LoadingSpinner? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }

    override fun onResume() {
        super.onResume()
        subscribeToLoadingState()
    }

    override fun onPause() {
        super.onPause()
        unsubscribeFromLoadingState()
    }

    abstract fun subscribeToViewModel()

    private fun subscribeToLoadingState() {
        viewModel.dialogSpinnerVisible.observe(viewLifecycleOwner) {
            if (it) showSpinner()
            else hideSpinner()
        }
    }

    private fun unsubscribeFromLoadingState() {
        viewModel.dialogSpinnerVisible.removeObservers(viewLifecycleOwner)
        hideSpinner()
    }

    private fun showSpinner() {
        if (loadingSpinner == null) {
            LoadingSpinner().let {
                loadingSpinner = it
                it.show(childFragmentManager, it.tag)
            }
        }
    }

    private fun hideSpinner() {
        loadingSpinner?.dismiss().also { loadingSpinner = null }
    }
}
