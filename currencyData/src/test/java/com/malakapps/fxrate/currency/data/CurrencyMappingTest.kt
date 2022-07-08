package com.malakapps.fxrate.currency.data

import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.currency.data.mappings.toDomain
import com.malakapps.fxrate.currency.data.mappings.toEntity
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CurrencyMappingTest {
    @Test
    fun `when getting currency domain model, map to database and back to domain`() {
        // given
        val currency = Currency("GBP", "British Pound Sterling")

        // then
        assertEquals(currency, currency.toEntity().toDomain())
    }
}
