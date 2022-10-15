package com.vhenri.stock_list_app.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.vhenri.stock_list_app.network.StocksApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val BASE_URL = "https://storage.googleapis.com/cash-homework/cash-stocks-api/"

    @Provides
    fun providesMoshi(): Moshi {
        // If we needed to, we can also add adapters to these before .build()!
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(context: Context): OkHttpClient {
        // If we needed to, we can also add adapters to these before .build()!
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}