package com.malakapps.fxrate.baseAndroid

import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan

fun SpannableString.applyRatio(string: String, ratio: Float) = apply {
    val start = indexOf(string)
    if (start != -1) setSpan(RelativeSizeSpan(ratio), start, start + string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}
