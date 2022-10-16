package com.vhenri.stock_list_app.network

import retrofit2.http.GET
import com.github.michaelbull.result.Result
import com.vhenri.stock_list_app.models.StockApiException
import com.vhenri.stock_list_app.models.StockList
import retrofit2.Response
import javax.inject.Inject

interface StocksApi {
    @GET("portfolio.json")
    suspend fun getPortfolio(): Response<StockList>

    @GET("portfolio_malformed.json")
    suspend fun getPortfolioMalformed(): Response<StockList>

    @GET("portfolio_empty.json")
    suspend fun getPortfolioEmpty(): Response<StockList>
}

class StocksApiClient @Inject constructor(private val api: StocksApi): BaseApiClient() {

    suspend fun getStocks(): Result<StockList?, StockApiException> {
        return execute { api.getPortfolio() }
    }
    suspend fun getStocksMalformed(): Result<StockList?, StockApiException> {
        return execute { api.getPortfolioMalformed() }
    }
    suspend fun getStocksEmpty(): Result<StockList?, StockApiException> {
        return execute { api.getPortfolioEmpty() }
    }
}