package com.malakapps.fxrate.currency.view.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.currency.view.list.adapter.viewholder.CurrencyViewHolder
import com.malakapps.fxrate.currencyview.databinding.CurrencyItemBinding

class CurrencyAdapter(
    private val onItemClicked: (Currency) -> Unit,
) : ListAdapter<Currency, CurrencyViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency) = oldItem.code == newItem.code
        override fun areContentsTheSame(oldItem: Currency, newItem: Currency) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CurrencyViewHolder(
        CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClicked,
    )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}
