package com.vhenri.stock_list_app.helpers

import com.vhenri.stock_list_app.models.Currency

fun Int.convertCentsToDollarString(currency: Currency = Currency.USD): String {
    return when(kotlin.math.abs(this)){
        in (0..99) -> "$0.${this}"
        else -> {
            val beforeDec = this/100
            val afterDec = this%100
            "$${beforeDec}.${afterDec}"
        }
    }
}