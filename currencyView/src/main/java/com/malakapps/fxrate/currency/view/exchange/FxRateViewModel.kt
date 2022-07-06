package com.malakapps.fxrate.currency.view.exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.model.ExchangeRate
import com.malakapps.fxrate.base.domain.usecase.GetExchangeRateUseCase
import com.malakapps.fxrate.baseAndroid.BaseViewModel
import com.malakapps.fxrate.baseAndroid.LiveDataEvent
import com.malakapps.fxrate.baseAndroid.view.edittext.AmountEditText
import com.malakapps.fxrate.basedomain.FlowUseCase
import com.malakapps.fxrate.basedomain.model.Amount
import com.malakapps.fxrate.basedomain.safeLet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.math.BigDecimal

class FxRateViewModel(
    private val getExchangeRateUseCase: GetExchangeRateUseCase,
) : BaseViewModel(), AmountEditText.AmountChangeListener {
    companion object {
        private const val INPUT_DELAY = 300L
    }

    private val sourceCurrency = MutableStateFlow<Currency?>(null)
    private val targetCurrency = MutableStateFlow<Currency?>(null)
    private val exchangeAmount = MutableStateFlow(BigDecimal.ZERO)

    private val exchangeRate = combine(sourceCurrency, targetCurrency, exchangeAmount) { source, target, _ ->
        safeLet(source, target, ::Pair)
    }.flatMapLatest { currencyPair ->
        if (exchangeAmount.value > BigDecimal.ZERO) {
            combineTransform(flowOf(currencyPair), exchangeAmount) { currencies, amount ->
                currencies?.let {
                    GetExchangeRateUseCase.Request(currencies.first, currencies.second, amount)
                }?.let { emit(it) }
            }.debounce(INPUT_DELAY)
                .flatMapLatest(getExchangeRateUseCase::call)
                .map(::getRate)
        } else {
            flowOf(null)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private fun getRate(result: FlowUseCase.Result<ExchangeRate>) = when (result) {
        is FlowUseCase.Result.Success -> result.result
        is FlowUseCase.Result.Failure -> null.also { Log.e("Error", result.error.toString()) }
    }

    val uiState = combine(sourceCurrency, targetCurrency, exchangeRate) { source, target, rate ->
        FxRateUiState(source, target, rate)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FxRateUiState(null, null, null))

    private val _navigate = MutableLiveData<LiveDataEvent<IFxRateFragmentNav.NavDestiny>>()
    val navigate: LiveData<LiveDataEvent<IFxRateFragmentNav.NavDestiny>>
        get() = _navigate

    override fun onAmountChanged(amount: Amount) {
        exchangeAmount.value = amount.value
    }

    fun onSourceCurrencySelected(currency: Currency) {
        sourceCurrency.value = currency
    }

    fun onTargetCurrencySelected(currency: Currency) {
        targetCurrency.value = currency
    }

    fun onSourceCurrencyClicked() {
        _navigate.value = LiveDataEvent(IFxRateFragmentNav.NavDestiny.CurrencyList(::onSourceCurrencySelected))
    }

    fun onTargetCurrencyClicked() {
        _navigate.value = LiveDataEvent(IFxRateFragmentNav.NavDestiny.CurrencyList(::onTargetCurrencySelected))
    }
}
