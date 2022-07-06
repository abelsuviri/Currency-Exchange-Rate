package com.malakapps.fxrate.currency.view.exchange

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.malakapps.fxrate.baseAndroid.formatter.CurrencyFormatter
import com.malakapps.fxrate.basedomain.model.Amount

@BindingAdapter("amount")
fun amount(view: TextView, amount: Amount?) {
    amount?.let {
        val currencyFormatter = CurrencyFormatter(view.context)
        view.text = currencyFormatter.format(amount)
    }
}

@BindingAdapter("ratePair")
fun ratePair(view: TextView, ratePair: Pair<Amount, Amount>?) {
    ratePair?.let {
        val currencyFormatter = CurrencyFormatter(view.context)
        view.text = "${currencyFormatter.format(it.first)} = ${currencyFormatter.format(it.second)}"
    }
}
