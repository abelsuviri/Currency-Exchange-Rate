package com.malakapps.fxrate.currencyview

import com.malakapps.fxrate.androidTestUtil.CoroutineTest
import com.malakapps.fxrate.androidTestUtil.awaitValue
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.model.ExchangeRate
import com.malakapps.fxrate.base.domain.usecase.GetExchangeRateUseCase
import com.malakapps.fxrate.basedomain.FlowUseCase
import com.malakapps.fxrate.basedomain.model.Amount
import com.malakapps.fxrate.currency.view.exchange.FxRateUiState
import com.malakapps.fxrate.currency.view.exchange.FxRateViewModel
import com.malakapps.fxrate.currency.view.exchange.IFxRateFragmentNav.NavDestiny
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FxRateViewModelTest : CoroutineTest() {
    private val getExchangeRateUseCase = mockk<GetExchangeRateUseCase>()
    private lateinit var viewModel: FxRateViewModel

    private val srcCurrency = Currency("GBP", "British Pound Sterling")
    private val targetCurrency = Currency("EUR", "Euro")
    private val amount = 1.5.toBigDecimal()
    private val exchangeRate = ExchangeRate(
        Amount(1.741343, "EUR"),
        Pair(Amount(1.0, "GBP", 0), Amount(1.160895, "EUR", 6)),
        "2022-07-04"
    )

    override fun setup() {
        super.setup()
        coEvery {
            getExchangeRateUseCase(GetExchangeRateUseCase.Request(srcCurrency, targetCurrency, amount))
        } returns flowOf(FlowUseCase.Result.Success(exchangeRate))
        coEvery {
            getExchangeRateUseCase(GetExchangeRateUseCase.Request(targetCurrency, srcCurrency, amount))
        } returns flowOf(FlowUseCase.Result.Failure())
        viewModel = FxRateViewModel(getExchangeRateUseCase)
    }

    @Test
    fun `when initialising ViewModel, set the default state`() {
        assertEquals(FxRateUiState(null, null, null), viewModel.uiState.value)
    }

    @Test
    fun `when filling the form with invalid exchange, set the ui state`() = runTest {
        // when
        viewModel.onSourceCurrencySelected(targetCurrency)
        viewModel.onTargetCurrencySelected(srcCurrency)
        viewModel.onAmountChanged(Amount(amount, srcCurrency.code))

        // then
        assertEquals(FxRateUiState(targetCurrency, srcCurrency, null), viewModel.uiState.first())
    }

    @Test
    fun `when source currency clicked, navigate to currency selection`() {
        // when
        viewModel.onSourceCurrencyClicked()

        // then
        assertEquals(NavDestiny.CurrencyList(viewModel::onSourceCurrencySelected), viewModel.navigate.awaitValue().consume())
    }

    @Test
    fun `when target currency clicked, navigate to currency selection`() {
        // when
        viewModel.onTargetCurrencyClicked()

        // then
        assertEquals(NavDestiny.CurrencyList(viewModel::onTargetCurrencySelected), viewModel.navigate.awaitValue().consume())
    }

    @Test
    fun `when source account selected, update value`() = runTest {
        // when
        viewModel.onSourceCurrencySelected(srcCurrency)

        // then
        assertEquals(FxRateUiState(srcCurrency, null, null), viewModel.uiState.first())
    }

    @Test
    fun `when target account selected, update value`() = runTest {
        // when
        viewModel.onTargetCurrencySelected(targetCurrency)

        // then
        assertEquals(FxRateUiState(null, targetCurrency, null), viewModel.uiState.first())
    }

    @Test
    fun `when currencies selected but amount is still zero, do not get the exchange rate`() {
        // when
        viewModel.onSourceCurrencySelected(srcCurrency)
        viewModel.onSourceCurrencySelected(targetCurrency)

        // then
        coVerify(exactly = 0) { getExchangeRateUseCase(any()) }
    }
}
