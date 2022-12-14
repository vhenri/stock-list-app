package com.vhenri.stock_list_app.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.vhenri.stock_list_app.R
import com.vhenri.stock_list_app.databinding.FragmentMainBinding
import com.vhenri.stock_list_app.models.Stock
import com.vhenri.stock_list_app.repo.ApiType
import com.vhenri.stock_list_app.ui.main.stocks.StocksListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    @Inject
    lateinit var viewModel: MainViewModel
    private val adapter = StocksListAdapter{viewModel.onStockCellClicked(it)}

    override fun onAttach(context: Context) {
        viewModel = (requireActivity() as MainActivity).viewModel
        super.onAttach(context)
    }

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
                launch(){
                    viewModel.navigation.collect {
                        if (it != null) {
                            findNavController().navigate(it)
                        }
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