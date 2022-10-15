package com.vhenri.stock_list_app.repo

import com.vhenri.stock_list_app.models.Stock
import com.vhenri.stock_list_app.network.ApiResultMonad
import com.vhenri.stock_list_app.network.ApiType
import com.vhenri.stock_list_app.network.StocksApiClient
import javax.inject.Inject

// Note - because this application is so small, we really don't need this file.
//        However, if our app grows, this will be helpful for providing an additional layer of abstraction
//        and we won't need to do so much in our VMs!

class StockDataRepository @Inject constructor(private val stocksApiClient: StocksApiClient){
    suspend fun getStockList(apiType: ApiType): ApiResultMonad<List<Stock>> {
        return stocksApiClient.getStocks(apiType)
    }
}