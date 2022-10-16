package com.vhenri.stock_list_app.network

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.vhenri.stock_list_app.models.StockApiException
import com.vhenri.stock_list_app.models.StockApiResponseException
import retrofit2.Response

abstract class BaseApiClient() {

    protected suspend fun <T> execute(block: suspend () -> Response<T>): Result<T?, StockApiException> {
        return kotlin.runCatching {
            block.invoke()
        }.fold(
            onSuccess = {
                if (it.isSuccessful) {
                    Ok(it.body())
                } else {
                    Err(StockApiResponseException(it.errorBody().toString()))
                }
            },
            onFailure = {
                Err(StockApiException(it))
            }
        )
    }
}