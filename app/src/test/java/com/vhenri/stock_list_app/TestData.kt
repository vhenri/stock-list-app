package com.vhenri.stock_list_app

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.vhenri.stock_list_app.models.Stock
import com.vhenri.stock_list_app.models.StockApiResponseException
import com.vhenri.stock_list_app.models.StockList

fun stockListResponseGood() = Ok(StockList(listOf(
Stock("TST", "test", "USD", 3333, null, 1636657688),
Stock("TST2", "test2", "USD", 3333, 13, 1636657688),
)))

fun stockListResponseEmpty() = Ok(StockList(emptyList()))

fun stockListResponseBad() = Err(StockApiResponseException("Test stock list response"))