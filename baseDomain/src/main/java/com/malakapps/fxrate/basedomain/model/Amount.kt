package com.malakapps.fxrate.basedomain.model

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency

data class Amount(
    val value: BigDecimal,
    val currency: String
) {
    constructor(value: Double, currency: String, fractionDigits: Int? = null) : this(
        value.toBigDecimal().setScale(
            fractionDigits ?: currency.runCatching { Currency.getInstance(this).defaultFractionDigits }.getOrNull() ?: 2,
            RoundingMode.HALF_DOWN
        ),
        currency
    )
}
