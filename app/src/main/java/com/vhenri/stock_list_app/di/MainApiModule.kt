package com.vhenri.stock_list_app.di

import com.vhenri.stock_list_app.network.FinnhubApi
import com.vhenri.stock_list_app.network.StocksApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
object MainApiModule {
    @Provides
    fun providesStocksApi(retrofit: Retrofit): StocksApi = retrofit.create()

    @Provides
    fun providesFinnhubApi(retrofit: Retrofit): FinnhubApi = retrofit.create()
}