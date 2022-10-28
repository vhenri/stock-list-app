package com.vhenri.stock_list_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.github.michaelbull.result.mapEither
import com.vhenri.stock_list_app.models.CompanyProfile
import com.vhenri.stock_list_app.models.Stock
import com.vhenri.stock_list_app.models.getUserExceptionMsg
import com.vhenri.stock_list_app.repo.ApiType
import com.vhenri.stock_list_app.repo.StockDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val stockDataRepository: StockDataRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState.MainUiState(
            null,
            null,
            null
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _navigation: MutableStateFlow<NavDirections?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private var selectedStock: Stock? = null

    fun getStocksData(apiType: ApiType){
        _isLoading.update { true }
        viewModelScope.launch {
            stockDataRepository.getStockList(apiType).mapEither(
                success = { stockList ->
                    val stocks = stockList?.stocks
                    if (stocks?.isNotEmpty() == true) {
                        _uiState.update {
                            UiState.MainUiState(
                                stocks,
                                null,
                                null
                            )
                        }
                    } else {
                        _uiState.update {
                            UiState.MainUiState(
                                null,
                                UiErrorType.EMPTY_LIST,
                                "🫙 Uh oh, the Stock List is empty!"
                            )
                        }
                    }
                },
                failure = {
                    val error = "🚨 Oh no, something went wrong! Unable to fetch stock data. \n\n${it.getUserExceptionMsg()}"
                    _uiState.update {
                        UiState.MainUiState(
                            null,
                            UiErrorType.DATA_ERROR,
                            error
                        )
                    }
                }
            )
            _isLoading.update { false }
        }
    }

    fun onStockCellClicked(stock: Stock){
        selectedStock = stock
        _navigation.update {
            MainFragmentDirections.mainFragmentToStockDetailsFragment()
        }
    }

    fun getSelectedStock() : Stock? {
        return selectedStock
    }

    fun getCompanyProfileBySymbol(){
        selectedStock?.ticker?.let { ticker ->
            viewModelScope.launch {
                stockDataRepository.getCompanyProfileBySymbol(ticker).mapEither(
                    success = { profile ->
                        _uiState.update {
                            UiState.DetailUiState(
                                profile,
                                null,
                                null
                            )
                        }
                    },
                    failure = {
                        val error = "Unable to get Company Profile at this time"
                        _uiState.update {
                            UiState.DetailUiState(
                                null,
                                UiErrorType.DATA_ERROR,
                                error
                            )
                        }
                    }
                )
            }
        }
    }
}

sealed class UiState {
    abstract val errorType: UiErrorType?
    abstract val errorString: String?

    data class MainUiState(
        val stockList: List<Stock>?,
        override val errorType: UiErrorType?,
        override val errorString: String?
    ) : UiState()

    data class DetailUiState(
        val companyProfile: CompanyProfile?,
        override val errorType: UiErrorType?,
        override val errorString: String?
    ) : UiState()
}

enum class UiErrorType {
    EMPTY_LIST, DATA_ERROR
}