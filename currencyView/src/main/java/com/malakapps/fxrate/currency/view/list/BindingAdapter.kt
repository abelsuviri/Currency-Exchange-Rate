package com.malakapps.fxrate.currency.view.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.malakapps.fxrate.baseAndroid.view.TextDrawable
import java.util.Currency

@BindingAdapter("currencyCode")
fun setCurrencyLogo(view: ImageView, currencyCode: String?) {
    currencyCode?.let {
        val currencySymbol = Currency.getInstance(currencyCode).symbol
        Glide.with(view.context)
            .load("")
            .placeholder(TextDrawable.primaryStyle(view.context, currencySymbol))
            .circleCrop()
            .into(view)
    }
}
