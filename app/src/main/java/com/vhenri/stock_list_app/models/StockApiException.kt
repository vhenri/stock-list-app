package com.vhenri.stock_list_app.models

open class StockException(t: Throwable? = null) : Exception()

open class StockApiException(t: Throwable? = null) : StockException(t)

class StockApiResponseException(errorBody: String) : StockApiException()

class StockMalformedJsonException(errorBody: String): StockApiException()

class EmptyResponseBodyException : StockApiException()

class NetworkException(t: Throwable) : StockApiException(t)

class UndefinedException(t: Throwable) : StockApiException(t)

fun StockException.getUserExceptionMsg() : String {
    return when(this){
        is StockMalformedJsonException -> "Reason: Malformed Json"
        else -> "Reason: Unknown"
    }
}