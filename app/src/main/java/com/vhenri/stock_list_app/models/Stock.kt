package com.vhenri.stock_list_app.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockList(
    val stocks: List<Stock>
)

@JsonClass(generateAdapter = true)
data class Stock(
    val ticker: String,
    val name: String,
    val currency: String,
    @Json(name = "current_price_cents") val currentPriceCents: Int,
    val quantity: Int?,
    @Json(name = "current_price_timestamp") val timestamp: Int // TODO - make Instant?
)

// TODO - use enums for currency!
enum class Currency {
    USD,
}