package com.vhenri.stock_list_app.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapEither
import com.vhenri.stock_list_app.models.Stock
import com.vhenri.stock_list_app.repo.ApiType
import com.vhenri.stock_list_app.repo.StockDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val stockDataRepository: StockDataRepository) : ViewModel() {
    private val _stockList: MutableStateFlow<List<Stock>> = MutableStateFlow(emptyList())
    val stockList = _stockList.asStateFlow()

    private val _errorState: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorState = _errorState.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _isLoading.asStateFlow()

    fun getStocksData(apiType: ApiType){
        viewModelScope.launch {
            stockDataRepository.getStockList(apiType).mapEither(
                success = {
                    Log.d("###", it?.stocks.toString())
                    _stockList.update{it}
                },
                failure = {
                    val error = "Oh no, something went wrong! Unable to fetch stock data :("
                    // TODO - Log error, show toast
                    _errorState.update { error }
                }
            )
        }
    }
}