package com.malakapps.fxrate.baseAndroid.view.edittext

import android.content.Context
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.malakapps.fxrate.baseAndroid.R
import com.malakapps.fxrate.baseAndroid.formatter.CurrencyFormatter
import com.malakapps.fxrate.basedomain.model.Amount
import java.math.BigDecimal

class AmountEditText(context: Context, attrs: AttributeSet? = null) :
    AppCompatEditText(context, attrs) {

    interface AmountChangeListener {
        fun onAmountChanged(amount: Amount)
    }

    private var formatter = CurrencyFormatter(context)

    var amountChangeListener: AmountChangeListener? = null

    var fractionDigits: Int
        get() = formatter.fractionDigits
        set(value) {
            val listener = amountChangeListener
            amountChangeListener = null
            formatter.fractionDigits = value
            renderNumber()
            amountChangeListener = listener
        }

    private var rawAmount = Amount(BigDecimal.ZERO.setScale(fractionDigits), null)
    private var number: BigDecimal
        get() = rawAmount.value
        set(value) {
            rawAmount = rawAmount.copy(value = value)
        }

    var currencyCode: String?
        get() = rawAmount.currency
        set(value) {
            val listener = amountChangeListener
            amountChangeListener = null
            value?.let { formatter.currencyCode = it }
            rawAmount = rawAmount.copy(currency = value)
            renderNumber()
            amountChangeListener = listener
        }

    var value: Double
        get() = number.toDouble()
        set(value) {
            number = value.toBigDecimal()
        }

    var amount: Amount
        get() = rawAmount
        set(value) {
            rawAmount = value
            renderNumber()
        }

    private val numberRegex by lazy { Regex("""[^0-9]""") }

    private val textWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                text?.let { editableText ->
                    number = editableText.toString().runCatching {
                        replace(numberRegex, "").toBigDecimal().movePointLeft(fractionDigits)
                    }.getOrDefault(BigDecimal.ZERO)
                }
                amountChangeListener?.onAmountChanged(rawAmount)
                removeTextChangedListener(this)
                renderNumber()
                addTextChangedListener(this)
            }
        }
    }

    init {
        inputType = InputType.TYPE_CLASS_NUMBER
        getAttributes(attrs)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addTextChangedListener(textWatcher)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeTextChangedListener(textWatcher)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        removeTextChangedListener(textWatcher)
        super.onRestoreInstanceState(state)
        addTextChangedListener(textWatcher)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        text?.toString()?.run {
            val position = indexOfLast { it.isDigit() }
                .takeIf { it != -1 }
                ?.let { it + 1 }
                ?: length
            if (selStart != position || selEnd != position) {
                setSelection(position, position)
            } else {
                super.onSelectionChanged(selStart, selEnd)
            }
        } ?: super.onSelectionChanged(selStart, selEnd)
    }


    // Private API
    private fun getAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AmountEditText,
            0, 0
        ).apply {

            try {
                currencyCode = getString(R.styleable.AmountEditText_currency)
                fractionDigits = getInt(R.styleable.AmountEditText_fractionDigits, formatter.fractionDigits)
                value = getFloat(R.styleable.AmountEditText_amount, 0.0f).toDouble()
            } finally {
                recycle()
            }
        }
    }

    private fun renderNumber() {
        text = Editable.Factory.getInstance().newEditable(formatter.format(number))
    }
}
