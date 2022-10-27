package com.vhenri.stock_list_app.di

import com.squareup.anvil.annotations.ContributesTo
import com.vhenri.stock_list_app.di.components.AppComponent
import com.vhenri.stock_list_app.network.StocksApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@[Module ContributesTo(AppComponent::class)]
object MainApiModule {
    @Provides
    fun providesStocksApi(retrofit: Retrofit): StocksApi = retrofit.create()
}