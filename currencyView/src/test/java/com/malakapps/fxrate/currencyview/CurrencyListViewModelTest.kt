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
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

abstract class CurrencyListViewModelTest : CoroutineTest() {
    companion object {
        val gbp = Currency("GBP", "British Pound Sterling")
        val eur = Currency("EUR", "Euro")
        val sortedCurrencies = listOf(gbp, eur,)
    }

    protected val getCurrencyListUseCase = mockk<GetCurrencyListUseCase>()
    protected lateinit var viewModel: CurrencyListViewModel

    private val currencyList = listOf(
        Currency("EUR", "Euro"),
        Currency("GBP", "British Pound Sterling"),
    )

    override fun setup() {
        super.setup()
        coEvery { getCurrencyListUseCase.call() } returns flowOf(FlowUseCase.Result.Success(currencyList))
        viewModel = CurrencyListViewModel(getCurrencyListUseCase)
    }
}

class SimpleTests : CurrencyListViewModelTest() {
    @Test
    fun `when initialising ViewModel, get the list of currencies`() {
        coVerify(exactly = 1) { getCurrencyListUseCase.call() }
    }

    @Test
    fun `when initialising ViewModel, set the list of currencies sorted by name`() = runTest {
        assertEquals(sortedCurrencies, viewModel.uiState.first().currencyList)
    }

    @Test
    fun `when currency clicked, navigate back passing the selected currency`() {
        // when
        viewModel.onCurrencyClicked(Currency("EUR", "Euro"))

        // then
        assertEquals(NavDestiny.PostResult(Currency("EUR", "Euro")), viewModel.navigate.awaitValue().consume())
    }
}

@RunWith(Parameterized::class)
class FilterTests(
    private val filter: String,
    private val filteredList: List<Currency>,
) : CurrencyListViewModelTest() {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf("", sortedCurrencies),
            arrayOf("e", sortedCurrencies),
            arrayOf("g", listOf(gbp)),
            arrayOf("eu", listOf(eur)),
            arrayOf("y", emptyList<Currency>()),
        )
    }

    @Test
    fun `when search filter typed, filter the list of currencies`() = runTest {
        // when
        viewModel.filterText.value = filter

        // then
        assertEquals(filteredList, viewModel.uiState.first().filteredCurrencies)
    }
}
