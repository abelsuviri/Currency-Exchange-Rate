package com.malakapps.fxrate.base.domain.model

import com.malakapps.fxrate.basedomain.model.Amount

data class ExchangeRate(
    val amount: Amount,
    val conversionRate: Pair<Amount, Amount>,
    val date: String,
)
