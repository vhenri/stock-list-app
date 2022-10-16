package com.vhenri.stock_list_app.models

open class StockDexException(t: Throwable? = null) : Exception()

open class StockApiException(t: Throwable? = null) : StockDexException(t)

class StockApiResponseException(errorBody: String) : StockApiException()

class EmptyResponseBodyException : StockApiException()

class NetworkException(t: Throwable) : StockApiException(t)

class UndefinedException(t: Throwable) : StockApiException(t)