package com.vhenri.stock_list_app

import app.cash.turbine.test
import com.vhenri.stock_list_app.repo.ApiType.PORTFOLIO
import com.vhenri.stock_list_app.repo.ApiType.MALFORMED
import com.vhenri.stock_list_app.repo.ApiType.EMPTY
import com.vhenri.stock_list_app.repo.StockDataRepository
import com.vhenri.stock_list_app.ui.main.MainViewModel
import com.vhenri.stock_list_app.ui.main.UiErrorType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class MainViewModelTest {

    @ExperimentalCoroutinesApi
    private val coroutineTestRule = CoroutineTestRule()
    @ExperimentalCoroutinesApi
    private val viewModelTestRule = ViewModelTestRule()
    @get:Rule
    val chain = RuleChain.outerRule(coroutineTestRule).around(viewModelTestRule)

    private val stockDataRepository = mockk<StockDataRepository>()

    private lateinit var viewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        coEvery { stockDataRepository.getStockList(PORTFOLIO) } returns stockListResponseGood()
        coEvery { stockDataRepository.getStockList(MALFORMED) } returns stockListResponseBad()
        coEvery { stockDataRepository.getStockList(EMPTY) } returns stockListResponseEmpty()
        viewModel = MainViewModel(stockDataRepository)
        viewModelTestRule.viewModel = viewModel
    }

    @Test
    fun `get stocks data happy path`() = runTest {
       viewModel.uiState.test{
           viewModel.getStocksData(PORTFOLIO)
           awaitItem() // skip init of empty
           val uiState = awaitItem()
           assertEquals(stockListResponseGood().value.stocks, uiState.stockList)
           assertNull(uiState.errorType)
           assertNull(uiState.errorString)
           cancelAndConsumeRemainingEvents()
       }
    }

    @Test
    fun `when stockList is not empty, if empty api is requested, list should be null, errorTyoe should be EMPTY_LIST and errorString should be not null`() = runTest {
        viewModel.uiState.test{
            viewModel.getStocksData(PORTFOLIO)
            viewModel.getStocksData(EMPTY)
            awaitItem() // vm init
            awaitItem() // happy path
            val uiState = awaitItem()
            assertNull(uiState.stockList)
            assertEquals(UiErrorType.EMPTY_LIST, uiState.errorType)
            assertNotNull(uiState.errorString)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when malformed api is requested, list should be null, errorTyoe should be DATA_ERROR and errorString should be not null`() = runTest {
        viewModel.uiState.test{
            viewModel.getStocksData(MALFORMED)
            awaitItem() // vm init
            val uiState = awaitItem()
            assertNull(uiState.stockList)
            assertEquals(UiErrorType.DATA_ERROR, uiState.errorType)
            assertNotNull(uiState.errorString)
            cancelAndConsumeRemainingEvents()
        }
    }
    @Test
    fun `when getStocksData is called, isLoading should be set to true, then false `() = runTest {
        viewModel.isLoading.test{
            viewModel.getStocksData(PORTFOLIO)
            awaitItem() // vm init of false
            assertTrue(awaitItem())
            assertFalse(awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

}