package com.malakapps.fxrate.baseAndroid

import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
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

fun EditText.setOnClearListener(callback: () -> Unit) {
    setOnTouchListener(View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= right -
                compoundDrawables[2].bounds.width() - paddingEnd
            ) {
                callback()
                return@OnTouchListener true
            }
        }
        performClick()
    })
}
