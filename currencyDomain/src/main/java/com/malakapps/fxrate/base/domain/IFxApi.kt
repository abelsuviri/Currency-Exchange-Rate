package com.malakapps.fxrate.base.domain

import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.model.ExchangeRate
import java.math.BigDecimal

interface IFxApi {
    suspend fun getCurrencyList(): List<Currency>?

    suspend fun getExchangeRate(source: Currency, target: Currency, amount: BigDecimal): ExchangeRate?
}
