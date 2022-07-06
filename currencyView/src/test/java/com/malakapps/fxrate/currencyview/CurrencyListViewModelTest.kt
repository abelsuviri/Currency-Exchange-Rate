package com.malakapps.fxrate.currencyview

import com.malakapps.fxrate.androidTestUtil.CoroutineTest
import com.malakapps.fxrate.androidTestUtil.awaitValue
import com.malakapps.fxrate.base.domain.model.Currency
import com.malakapps.fxrate.base.domain.usecase.GetCurrencyListUseCase
import com.malakapps.fxrate.basedomain.FlowUseCase
import com.malakapps.fxrate.basedomain.call
import com.malakapps.fxrate.currency.view.list.CurrencyListViewModel
import com.malakapps.fxrate.currency.view.list.ICurrencyListFragmentNav.NavDestiny
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CurrencyListViewModelTest : CoroutineTest() {
    private val getCurrencyListUseCase = mockk<GetCurrencyListUseCase>()
    private lateinit var viewModel: CurrencyListViewModel

    private val currencyList = listOf(
        Currency("EUR", "Euro"),
        Currency("GBP", "British Pound Sterling"),
    )

    override fun setup() {
        super.setup()
        coEvery { getCurrencyListUseCase.call() } returns flowOf(FlowUseCase.Result.Success(currencyList))
        viewModel = CurrencyListViewModel(getCurrencyListUseCase)
    }

    @Test
    fun `when initialising ViewModel, get the list of currencies`() {
        coVerify(exactly = 1) { getCurrencyListUseCase.call() }
    }

    @Test
    fun `when initialising ViewModel, set the list of currencies sorted by name`() = runTest {
        val sortedCurrencies = listOf(
            Currency("GBP", "British Pound Sterling"),
            Currency("EUR", "Euro"),
        )

        assertEquals(sortedCurrencies, viewModel.currencyList.first())
    }

    @Test
    fun `when currency clicked, navigate back passing the selected currency`() {
        // when
        viewModel.onCurrencyClicked(Currency("EUR", "Euro"))

        // then
        assertEquals(NavDestiny.PostResult(Currency("EUR", "Euro")), viewModel.navigate.awaitValue().consume())
    }
}
