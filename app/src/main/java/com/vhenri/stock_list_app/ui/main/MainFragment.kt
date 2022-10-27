package com.vhenri.stock_list_app.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.squareup.anvil.annotations.ContributesMultibinding
import com.vhenri.stock_list_app.R
import com.vhenri.stock_list_app.databinding.FragmentMainBinding
import com.vhenri.stock_list_app.di.FragmentKey
import com.vhenri.stock_list_app.di.ViewModelFactory
import com.vhenri.stock_list_app.di.components.AppComponent
import com.vhenri.stock_list_app.models.Stock
import com.vhenri.stock_list_app.repo.ApiType
import kotlinx.coroutines.launch
import javax.inject.Inject

@[ContributesMultibinding(AppComponent::class, Fragment::class) FragmentKey(MainFragment::class)]
class MainFragment @Inject constructor(
    viewModelFactory: ViewModelFactory
) : Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    private val adapter = StocksListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.stocksRv.adapter = adapter
        viewModel.getStocksData(ApiType.PORTFOLIO)
        initObservables()
        initBindings()
    }

    private fun initObservables(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch() {
                    viewModel.uiState.collect { state ->
                        if (!state.stockList.isNullOrEmpty()) {
                            updateList(state.stockList)
                        } else if (!state.errorString.isNullOrEmpty()){
                            updateNoListData(state.errorType, state.errorString)
                        }
                    }
                }
                launch(){
                    viewModel.isLoading.collect{
                        binding.loading.isVisible = it
                    }
                }
            }
        }
    }

    private fun initBindings(){
        binding.buttonGood.setOnClickListener{
            viewModel.getStocksData(ApiType.PORTFOLIO)
        }
        binding.buttonMalformed.setOnClickListener {
            viewModel.getStocksData(ApiType.MALFORMED)
        }
        binding.buttonEmpty.setOnClickListener {
            viewModel.getStocksData(ApiType.EMPTY)
        }
    }

    private fun updateList(list: List<Stock>){
        binding.stocksRv.isVisible = true
        binding.noListView.isVisible = false
        adapter.list = list
    }

    private fun updateNoListData(errorType: UiErrorType?, message: String?) {
        val drawable = if (errorType == UiErrorType.EMPTY_LIST) R.drawable.img_no_list else R.drawable.img_error
        binding.stocksRv.isVisible = false
        binding.noListView.isVisible = true
        binding.noListText.text = message
        binding.noListImg.setImageResource(drawable)
    }

}