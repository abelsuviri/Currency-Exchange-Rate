package com.malakapps.fxrate.api

import com.malakapps.fxrate.api.fx.FxService
import com.malakapps.fxrate.base.IFxApi
import com.malakapps.fxrate.base.domain.model.Currency
import java.math.BigDecimal

class ApiClient(
    private val fxService: FxService,
) : IFxApi {

    override suspend fun getCurrencyList() = fxService.getCurrencyList()?.toCurrency()

    override suspend fun getExchangeRate(source: Currency, target: Currency, amount: BigDecimal) =
        fxService.getExchangeRate(source.code, target.code, amount.toDouble())?.toExchangeRate()
}
