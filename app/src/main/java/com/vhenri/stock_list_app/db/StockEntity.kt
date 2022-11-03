package com.vhenri.stock_list_app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_table")
data class StockEntity(
    @PrimaryKey val ticker: String,
    val name: String,
    val currency: String,
    val currentPriceCents: Int,
    val quantity: Int?,
    val timestamp: Int,
)