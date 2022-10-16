package com.vhenri.stock_list_app.repo

import com.github.michaelbull.result.Result
import com.vhenri.stock_list_app.models.StockApiException
import com.vhenri.stock_list_app.models.StockList
import com.vhenri.stock_list_app.network.StocksApiClient
import javax.inject.Inject

class StockDataRepository @Inject constructor(private val stocksApiClient: StocksApiClient){
    suspend fun getStockList(apiType: ApiType): Result<StockList?, StockApiException> {
        return when (apiType) {
            ApiType.PORTFOLIO -> {
                stocksApiClient.getStocks()
            }
            ApiType.MALFORMED -> {
                stocksApiClient.getStocksMalformed()
            }
            ApiType.EMPTY -> {
                stocksApiClient.getStocksEmpty()
            }
        }
    }
}

enum class ApiType { PORTFOLIO, MALFORMED, EMPTY}