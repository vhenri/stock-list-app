package com.vhenri.stock_list_app.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vhenri.stock_list_app.R
import com.vhenri.stock_list_app.StockListApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as StockListApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, MainFragment())
                .commit()
        }
    }
}