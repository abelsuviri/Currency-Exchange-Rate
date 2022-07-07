package com.malakapps.fxrate.baseAndroid

import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun SpannableString.applyRatio(string: String, ratio: Float) = apply {
    val start = indexOf(string)
    if (start != -1) setSpan(RelativeSizeSpan(ratio), start, start + string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}

@ColorInt
fun Resources.Theme.resolveColor(@AttrRes resId: Int): Int =
    TypedValue().let {
        resolveAttribute(resId, it, true)
        it.data
    }
