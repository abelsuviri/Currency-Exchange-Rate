package com.malakapps.fxrate.currency.view.exchange

import com.malakapps.fxrate.base.domain.model.Currency

interface IFxRateFragmentNav {
    sealed class NavDestiny {
        data class CurrencyList(val callback: (Currency) -> Unit) : NavDestiny()
    }
    fun navigateToCurrencyList(instance: FxRateFragment, callback : (Currency) -> Unit)
}
