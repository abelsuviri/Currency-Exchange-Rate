package com.malakapps.fxrate.currency.view.list.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.currencyview.databinding.CurrencyItemBinding

class CurrencyViewHolder(
    private val binding: CurrencyItemBinding,
    private val onItemClicked: (Currency) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bindItem(item: Currency) {
        binding.currency = item
        binding.root.setOnClickListener { onItemClicked(item) }
    }
}
