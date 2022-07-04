package com.malakapps.fxrate.base.domain.usecase

import com.malakapps.fxrate.basedomain.FlowUseCase
import com.malakapps.fxrate.base.FxRepository
import com.malakapps.fxrate.base.domain.model.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class GetCurrencyListUseCase(
    private val fxRepository: FxRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, List<Currency>>(dispatcher) {
    override suspend fun performAction(param: Unit) = fxRepository.getCurrencyList().map { Result.ofNullable(it) }
}
