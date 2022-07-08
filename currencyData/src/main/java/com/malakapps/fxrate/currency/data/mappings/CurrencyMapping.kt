package com.malakapps.fxrate.currency.data.mappings

import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.currency.data.entity.CurrencyEntity

fun CurrencyEntity.toDomain() = Currency(code, name)

fun Currency.toEntity() = CurrencyEntity(code, name)
