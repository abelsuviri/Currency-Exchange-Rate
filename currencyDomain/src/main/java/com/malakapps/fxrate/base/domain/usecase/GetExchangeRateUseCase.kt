package com.malakapps.fxrate.base.domain.usecase

import com.malakapps.fxrate.basedomain.FlowUseCase
import com.malakapps.fxrate.base.FxRepository
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.model.ExchangeRate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class GetExchangeRateUseCase(
    private val fxRepository: FxRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<GetExchangeRateUseCase.Request, ExchangeRate>(dispatcher) {
    data class Request(val source: Currency, val target: Currency, val amount: BigDecimal)

    override suspend fun performAction(param: Request) = flow {
        val response = fxRepository.getExchangeRate(param.source, param.target, param.amount)
        emit(Result.ofNullable(response))
    }
}
