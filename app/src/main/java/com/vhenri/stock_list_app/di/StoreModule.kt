package com.vhenri.stock_list_app.di

import android.app.Application
import androidx.room.Room
import com.vhenri.stock_list_app.StockListApplication
import com.vhenri.stock_list_app.db.StockDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
abstract class StoreModule() {

    @Binds
    abstract fun application(app: StockListApplication?): Application?

    companion object {
        private val DB_NAME = "stock_db"

        @Singleton
        @Provides
        fun provideAppDatabase(awareApp: Application): StockDatabase? {
            return Room
                .databaseBuilder(awareApp.applicationContext, StockDatabase::class.java, DB_NAME)
                .build()
        }
    }
}