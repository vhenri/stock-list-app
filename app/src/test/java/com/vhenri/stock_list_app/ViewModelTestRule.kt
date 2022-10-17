package com.vhenri.stock_list_app

import androidx.lifecycle.ViewModel
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ViewModelTestRule : TestWatcher() {
    lateinit var viewModel: ViewModel

    // Clear out viewmodel scope
    override fun finished(description: Description) {
        super.finished(description)
        with(ViewModel::class.java.getDeclaredMethod("clear")) {
            isAccessible = true
            invoke(viewModel)
        }
    }
}