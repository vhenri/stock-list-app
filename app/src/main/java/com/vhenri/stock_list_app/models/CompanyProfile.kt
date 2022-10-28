package com.vhenri.stock_list_app.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyProfile(
    val country : String,
    val exchange: String,
    @Json(name = "ipo") val ipoDate: String,
    @Json(name = "phone") val phoneNumber: String,
    val webUrl: String,
    @Json(name = "logo") val logoUrl: String,
    val finnhubIndustry: String
)