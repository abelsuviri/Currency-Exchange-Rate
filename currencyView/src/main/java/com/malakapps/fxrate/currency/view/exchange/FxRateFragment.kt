package com.malakapps.fxrate.currency.view.exchange

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.malakapps.fxrate.base.domain.usecase.GetExchangeRateUseCase
import com.malakapps.fxrate.baseAndroid.BaseFragment
import com.malakapps.fxrate.baseAndroid.consume
import com.malakapps.fxrate.baseAndroid.view.viewAwareProperty
import com.malakapps.fxrate.currencyview.R
import com.malakapps.fxrate.currencyview.databinding.FragmentFxRateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FxRateFragment : BaseFragment<FxRateViewModel>(R.layout.fragment_fx_rate) {
    @Inject
    lateinit var getExchangeRateUseCase: GetExchangeRateUseCase

    @Inject
    lateinit var nav: IFxRateFragmentNav

    private val binding by viewAwareProperty { FragmentFxRateBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.sourceCurrency.selectButton.setOnClickListener { viewModel.onSourceCurrencyClicked() }
        binding.sourceCurrency.currencyItem.root.setOnClickListener { viewModel.onSourceCurrencyClicked() }
        binding.targetCurrency.selectButton.setOnClickListener { viewModel.onTargetCurrencyClicked() }
        binding.targetCurrency.currencyItem.root.setOnClickListener { viewModel.onTargetCurrencyClicked() }
    }

    override fun onStart() {
        super.onStart()
        binding.amountEditText.amountChangeListener = viewModel
    }

    override fun onStop() {
        binding.amountEditText.amountChangeListener = null
        super.onStop()
    }

    override fun createViewModel() = FxRateViewModel(getExchangeRateUseCase)

    override fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { state ->
                    state.sourceCurrency?.let { binding.amountEditText.currencyCode = it.code }
                }
            }
        }

        viewModel.navigate.consume(viewLifecycleOwner) { destiny ->
            when (destiny) {
                is IFxRateFragmentNav.NavDestiny.CurrencyList -> nav.navigateToCurrencyList(this, destiny.callback)
            }
        }
    }
}
