package com.vhenri.stock_list_app.network

import com.vhenri.stock_list_app.models.Stock
import retrofit2.http.GET
import com.github.michaelbull.result.Result
import javax.inject.Inject

typealias ApiResultMonad<T> = Result<T, Throwable>

interface StocksApi {
    @GET("portfolio.json")
    suspend fun getPortfolio(): ApiResultMonad<List<Stock>>

    @GET("portfolio_malformed.json")
    suspend fun getPortfolioMalformed(): ApiResultMonad<List<Stock>>

    @GET("portfolio_empty.json")
    suspend fun getPortfolioEmpty(): ApiResultMonad<List<Stock>>
}

class StocksApiClient @Inject constructor(private val api: StocksApi){

    suspend fun getStocks(apiType: ApiType): ApiResultMonad<List<Stock>>{
        return when(apiType) {
            ApiType.PORTFOLIO -> {
                api.getPortfolio()
            }
            ApiType.MALFORMED -> {
                api.getPortfolioMalformed()
            }
            ApiType.EMPTY -> {
                api.getPortfolioEmpty()
            }
        }
    }
}

enum class ApiType { PORTFOLIO, MALFORMED, EMPTY}