package com.malakapps.fxrate.currency.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.usecase.GetCurrencyListUseCase
import com.malakapps.fxrate.baseAndroid.BaseViewModel
import com.malakapps.fxrate.baseAndroid.LiveDataEvent
import com.malakapps.fxrate.basedomain.call
import com.malakapps.fxrate.basedomain.data
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class CurrencyListViewModel(getCurrencyListUseCase: GetCurrencyListUseCase): BaseViewModel() {
    val currencyList = getCurrencyListUseCase.call().mapLatest { response ->
        response.data?.sortedBy { it.name } ?: emptyList()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _navigate = MutableLiveData<LiveDataEvent<ICurrencyListFragmentNav.NavDestiny>>()
    val navigate: LiveData<LiveDataEvent<ICurrencyListFragmentNav.NavDestiny>>
        get() = _navigate

    fun onCurrencyClicked(currency: Currency) {
        _navigate.value = LiveDataEvent(ICurrencyListFragmentNav.NavDestiny.PostResult(currency))
    }
}
