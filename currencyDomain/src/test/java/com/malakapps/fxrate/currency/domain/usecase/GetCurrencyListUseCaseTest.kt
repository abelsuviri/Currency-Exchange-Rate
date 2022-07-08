package com.malakapps.fxrate.currency.domain.usecase

import com.malakapps.fxrate.base.domain.FxRepository
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.usecase.GetCurrencyListUseCase
import com.malakapps.fxrate.basedomain.FlowUseCase
import com.malakapps.fxrate.basedomain.call
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
class GetCurrencyListUseCaseTest(
    private val repositoryResult: List<Currency>?,
    private val useCaseResult: FlowUseCase.Result<List<Currency>>,
) : UseCaseTest() {
    companion object {
        private val currency = Currency("GBP", "British Pound Sterling")

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(listOf(currency), FlowUseCase.Result.Success(listOf(currency))),
            arrayOf(null, FlowUseCase.Result.Failure()),
        )
    }

    @Test
    fun `when requesting currency list, return result`() = runTest {
        // given
        val repository = mockk<FxRepository>()
        coEvery { repository.getCurrencyList() } returns flowOf(repositoryResult)

        val useCase = GetCurrencyListUseCase(repository)

        // when
        val result = useCase.call()

        // then
        assertEquals(useCaseResult, result.first())
    }
}
