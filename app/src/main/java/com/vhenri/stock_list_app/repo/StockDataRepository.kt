package com.vhenri.stock_list_app.repo

import com.github.michaelbull.result.Result
import com.vhenri.stock_list_app.models.CompanyProfile
import com.vhenri.stock_list_app.models.StockApiException
import com.vhenri.stock_list_app.models.StockList
import com.vhenri.stock_list_app.network.FinnhubApiClient
import com.vhenri.stock_list_app.network.StocksApiClient
import javax.inject.Inject

interface StockDataRepositoryInterface {
    suspend fun getStockList(apiType: ApiType): Result<StockList?, StockApiException>
    suspend fun getCompanyProfileBySymbol(symbol: String): Result<CompanyProfile?, StockApiException>
}

class StockDataRepository @Inject constructor(
    private val stocksApiClient: StocksApiClient,
    private val finnhubApiClient: FinnhubApiClient
    ): StockDataRepositoryInterface {
    override suspend fun getStockList(apiType: ApiType): Result<StockList?, StockApiException> {
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

    override suspend fun getCompanyProfileBySymbol(symbol: String): Result<CompanyProfile?, StockApiException> {
        return finnhubApiClient.getCompanyProfileBySymbol(symbol)
    }


}

enum class ApiType { PORTFOLIO, MALFORMED, EMPTY }