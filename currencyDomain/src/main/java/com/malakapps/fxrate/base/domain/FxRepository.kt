package com.malakapps.fxrate.base.domain

import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.basedomain.RefreshControl
import com.malakapps.fxrate.basedomain.RepositoryResource
import java.math.BigDecimal

class FxRepository(
    private val networkSource: IFxApi,
    private val localResource: ICurrencyCache,
    parentControl: RefreshControl = RefreshControl()
) {
    private val currencyResource = RepositoryResource<Unit, List<Currency>>(
        { networkSource.getCurrencyList() },
        { localResource.getCurrencyList() },
        localResource::storeCurrencies,
        localResource::deleteAll,
        parentControl.createChild(),
    )

   suspend fun getCurrencyList() = currencyResource.query(Unit)

    suspend fun getExchangeRate(source: Currency, target: Currency, amount: BigDecimal) =
        networkSource.getExchangeRate(source, target, amount)
}
