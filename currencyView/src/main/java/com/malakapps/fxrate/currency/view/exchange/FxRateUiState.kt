package com.malakapps.fxrate.currency.view.exchange

import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.model.ExchangeRate

data class FxRateUiState(
    val sourceCurrency: Currency?,
    val targetCurrency: Currency?,
    val rate: ExchangeRate?
) {
    val ratePair = rate?.conversionRate
}
