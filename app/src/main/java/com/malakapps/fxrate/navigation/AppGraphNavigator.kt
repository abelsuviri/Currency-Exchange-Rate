package com.malakapps.fxrate.navigation

import androidx.navigation.fragment.findNavController
import com.malakapps.fxrate.R
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.currency.view.exchange.FxRateFragment
import com.malakapps.fxrate.currency.view.exchange.FxRateFragmentDirections
import com.malakapps.fxrate.currency.view.exchange.IFxRateFragmentNav
import com.malakapps.fxrate.currency.view.list.CurrencyListFragment
import com.malakapps.fxrate.currency.view.list.ICurrencyListFragmentNav

class AppGraphNavigator : IFxRateFragmentNav, ICurrencyListFragmentNav {
    companion object {
        private const val CURRENCY_RESULT_KEY = "CurrencyListFragment:result"
    }

    private val graphId = R.id.nav_graph

    override fun navigateToCurrencyList(instance: FxRateFragment, callback: (Currency) -> Unit) {
        with(instance.findNavController()) {
            observeResultForKey(instance, CURRENCY_RESULT_KEY, callback)
            navigate(FxRateFragmentDirections.actionToCurrencyList())
        }
    }

    override fun postResult(instance: CurrencyListFragment, currency: Currency) {
        with(instance.findNavController()) {
            postResult(CURRENCY_RESULT_KEY, currency)
            popBackStack()
        }
    }
}
