package com.malakapps.fxrate.currency.domain.usecase

import com.malakapps.fxrate.base.domain.FxRepository
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.model.ExchangeRate
import com.malakapps.fxrate.base.domain.usecase.GetExchangeRateUseCase
import com.malakapps.fxrate.basedomain.FlowUseCase
import com.malakapps.fxrate.basedomain.model.Amount
import com.malakapps.fxrate.testutil.UseCaseTest
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class GetExchangeRateUseCaseTest(
    private val repositoryResult: ExchangeRate?,
    private val useCaseResult: FlowUseCase.Result<ExchangeRate>,
) : UseCaseTest() {
    companion object {
        private val exchangeRate = ExchangeRate(
            Amount(1.741343, "EUR"),
            Pair(Amount(1.0, "GBP", 0), Amount(1.160895, "EUR", 6)),
            "2022-07-04"
        )

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(exchangeRate, FlowUseCase.Result.Success(exchangeRate)),
            arrayOf(null, FlowUseCase.Result.Failure()),
        )
    }

    @Test
    fun `when requesting exchange rate, return result`() = runTest {
        // given
        val srcCurrency = Currency("GBP", "British Pound Sterling")
        val targetCurrency = Currency("EUR", "Euro")
        val amount = 1.5.toBigDecimal()
        val repository = mockk<FxRepository>()
        coEvery { repository.getExchangeRate(srcCurrency, targetCurrency, amount) } returns flowOf(repositoryResult)

        val useCase = GetExchangeRateUseCase(repository)

        // when
        val result = useCase.call(GetExchangeRateUseCase.Request(srcCurrency, targetCurrency, amount))

        // then
        assertEquals(useCaseResult, result.first())
    }
}
