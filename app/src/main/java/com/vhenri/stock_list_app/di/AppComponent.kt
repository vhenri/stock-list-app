package com.vhenri.stock_list_app.di

import android.content.Context
import com.vhenri.stock_list_app.ui.main.MainActivity
import com.vhenri.stock_list_app.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
}