package com.vhenri.stock_list_app

import android.app.Application
import com.squareup.anvil.annotations.MergeComponent
import com.vhenri.stock_list_app.di.components.AppComponent
import com.vhenri.stock_list_app.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component

class StockListApplication : Application(){

    lateinit var component: AppMergeComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppMergeComponent.factory().create(this)
    }

}

@[AppComponent MergeComponent(scope = AppComponent::class)]
interface AppMergeComponent {
    fun inject(target:MainActivity)

    @Component.Factory
    interface Factory {
        fun create (
            @BindsInstance context: Application
        ): AppMergeComponent
    }
}