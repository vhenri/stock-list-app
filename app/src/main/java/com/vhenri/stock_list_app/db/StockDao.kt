package com.vhenri.stock_list_app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert
    fun insert(stock: StockEntity)

    @Query("SELECT * FROM stock_table")
    fun getAllStock(): Flow<List<StockEntity>>
}