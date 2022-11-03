package com.vhenri.stock_list_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StockEntity::class], version = 1 )
abstract class StockDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    companion object {
        private var instance: StockDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): StockDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    StockDatabase::class.java,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .build()
            return instance!!
        }
    }
}