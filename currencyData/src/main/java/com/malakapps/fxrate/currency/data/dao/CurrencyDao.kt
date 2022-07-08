package com.malakapps.fxrate.currency.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.malakapps.fxrate.base.data.BaseDao
import com.malakapps.fxrate.currency.data.entity.CurrencyEntity

@Dao
interface CurrencyDao : BaseDao<CurrencyEntity> {
    @Query("SELECT * from currency")
    suspend fun getAll(): List<CurrencyEntity>

    @Query("DELETE from currency")
    suspend fun deleteAll()
}

@Transaction
suspend fun CurrencyDao.replaceAll(vararg currencyEntity: CurrencyEntity) {
    deleteAll()
    insert(*currencyEntity)
}
