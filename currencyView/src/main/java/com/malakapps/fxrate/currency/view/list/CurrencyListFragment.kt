package com.malakapps.fxrate.currency.view.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.malakapps.fxrate.base.domain.usecase.GetCurrencyListUseCase
import com.malakapps.fxrate.baseAndroid.BaseFragment
import com.malakapps.fxrate.baseAndroid.consume
import com.malakapps.fxrate.baseAndroid.view.viewAwareProperty
import com.malakapps.fxrate.currency.view.list.adapter.CurrencyAdapter
import com.malakapps.fxrate.currencyview.R
import com.malakapps.fxrate.currencyview.databinding.FragmentCurrencyListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyListFragment : BaseFragment<CurrencyListViewModel>(R.layout.fragment_currency_list) {
    @Inject
    lateinit var getCurrencyListUseCase: GetCurrencyListUseCase

    @Inject
    lateinit var nav: ICurrencyListFragmentNav

    private val binding by viewAwareProperty { FragmentCurrencyListBinding.bind(requireView()) }

    private val adapter by viewAwareProperty { CurrencyAdapter(viewModel::onCurrencyClicked) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.currencyList.adapter = adapter
    }

    override fun createViewModel() = CurrencyListViewModel(getCurrencyListUseCase)

    override fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currencyList.collectLatest { currencyList ->
                    adapter.submitList(currencyList)
                }
            }
        }

        viewModel.navigate.consume(viewLifecycleOwner) { destiny ->
            when (destiny) {
                is ICurrencyListFragmentNav.NavDestiny.PostResult -> nav.postResult(this, destiny.currency)
            }
        }
    }
}
