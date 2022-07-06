package com.malakapps.fxrate.currency.view.list

import com.malakapps.fxrate.base.domain.model.Currency

interface ICurrencyListFragmentNav {
    sealed class NavDestiny {
        data class PostResult(val currency: Currency) : NavDestiny()
    }

    fun postResult(instance: CurrencyListFragment, currency: Currency)
}
