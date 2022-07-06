package com.malakapps.fxrate.baseAndroid.formatter

import android.content.Context
import android.text.SpannableString
import com.malakapps.fxrate.baseAndroid.applyRatio
import com.malakapps.fxrate.basedomain.model.Amount
import dagger.hilt.android.qualifiers.ActivityContext
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

class CurrencyFormatter @Inject constructor(@ActivityContext val context: Context) {
    companion object {
        const val SYMBOL_RATIO = 0.7f
    }

    private val locale: Locale = context.resources.configuration.locales[0]
    private val formatter: NumberFormat = NumberFormat.getCurrencyInstance(locale)

    var currency: Currency
        get() = formatter.currency ?: Currency.getInstance(locale)
        set(value) {
            formatter.currency = value
            formatter.maximumFractionDigits = value.defaultFractionDigits
            formatter.minimumFractionDigits = value.defaultFractionDigits
        }

    var currencyCode = currency.currencyCode

    var fractionDigits: Int
        get() = formatter.maximumFractionDigits
        set(value) {
            formatter.maximumFractionDigits = value
            formatter.minimumFractionDigits = value
        }

    fun format(amount: BigDecimal): CharSequence = formatter.format(amount).let(::SpannableString)
        .applyRatio(currency.getSymbol(locale), SYMBOL_RATIO)

    fun format(amount: Amount): CharSequence {
        amount.currency?.let { formatter.currency = Currency.getInstance(it) }
        formatter.maximumFractionDigits = amount.value.scale()
        formatter.minimumFractionDigits = amount.value.scale()

        return formatter.format(amount.value).let(::SpannableString).applyRatio(currencyCode, SYMBOL_RATIO)
    }
}
