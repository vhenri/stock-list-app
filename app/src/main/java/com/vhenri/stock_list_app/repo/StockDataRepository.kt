package com.vhenri.stock_list_app.repo

import android.content.Context
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import com.github.michaelbull.result.mapEither
import com.vhenri.stock_list_app.db.StockDao
import com.vhenri.stock_list_app.db.StockDatabase
import com.vhenri.stock_list_app.db.StockEntity
import com.vhenri.stock_list_app.models.StockApiException
import com.vhenri.stock_list_app.models.StockList
import com.vhenri.stock_list_app.models.getUserExceptionMsg
import com.vhenri.stock_list_app.network.StocksApiClient
import com.vhenri.stock_list_app.ui.main.MainUiState
import com.vhenri.stock_list_app.ui.main.UiErrorType
import java.util.concurrent.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.update

interface StockDataRepositoryInterface {
    suspend fun getStockList(apiType: ApiType): Result<StockList?, StockApiException>
}

class StockDataRepository @Inject constructor(
    private val stockDatabase: StockDatabase,
    private val stocksApiClient: StocksApiClient
    ): StockDataRepositoryInterface {

    private lateinit var stockDao: StockDao
    private lateinit var allStocks: Flow<List<StockEntity>>

    private val database = stockDatabase

    fun fetchStockLists(apiType: ApiType): StockList{
        // check if in database
        // if not getStockList then add to db
        // if is in database grab from database
    }

    fun insertStocksIntoDatabase(stockList: StockList){
       // TODO() - insert!
    }

    override suspend fun getStockList(apiType: ApiType): Result<StockList?, StockApiException> {
        // TODO - try to fetch stock list from the DB, otherwise you'll need to get it from the API
        return when (apiType) {
            ApiType.PORTFOLIO -> {
                val stockList = stocksApiClient.getStocks()
//                insertStocksIntoDatabase(stockList = stockList.get())
                stockList
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

enum class ApiType { PORTFOLIO, MALFORMED, EMPTY }