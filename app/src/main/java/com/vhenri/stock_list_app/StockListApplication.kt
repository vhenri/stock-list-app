package com.vhenri.stock_list_app

import android.app.Application
import com.vhenri.stock_list_app.di.AppComponent

open class StockListApplication : Application() {
    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent : AppComponent by lazy {
        initComponent()
    }

    open fun initComponent(): AppComponent {
        TODO()
//        return DaggerAppComponent.factory().create(applicationContext)
    }
}