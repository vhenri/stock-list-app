package com.vhenri.stock_list_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapEither
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
    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState(null, null, null,null, null))
    val uiState = _uiState.asStateFlow()


    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getStocksData(apiType: ApiType){
        _isLoading.update { true }
        viewModelScope.launch {
            stockDataRepository.getStockList(apiType).mapEither(
                success = { stockList ->
                    val stocks = stockList?.stocks
                    if (stocks?.isNotEmpty() == true) {
                        _uiState.update {
                            MainUiState(
                                stocks,
                                null,
                                null,
                                null,
                                null
                            )
                        }
                    } else {
                        _uiState.update {
                            MainUiState(
                                null,
                                null,
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
                        MainUiState(
                            null,
                            null,
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

    fun onSearchTextChanged(text: CharSequence){
        val stockList = uiState.value.stockList
        // search for company name & ticker

        val newList = if (text.isNotEmpty()) {
            stockList?.filter { stock -> stock.name.contains(text) || stock.ticker.contains(text) }
        } else {
            stockList
        }
        _uiState.update {
            MainUiState(
                stockList,
                newList,
                filteredText = text.toString(),
                null,
                null
            )
        }

    }
}

data class MainUiState(
    val stockList: List<Stock>?,
    val filteredStockList: List<Stock>?,
    val filteredText: String?,
    val errorType: UiErrorType?,
    val errorString: String?
)

enum class UiErrorType {
    EMPTY_LIST, DATA_ERROR
}