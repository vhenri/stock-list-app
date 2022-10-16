package com.vhenri.stock_list_app.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vhenri.stock_list_app.databinding.FragmentMainBinding
import com.vhenri.stock_list_app.repo.ApiType
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    @Inject
    lateinit var viewModel: MainViewModel
    private val adapter = StocksListAdapter()

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
    }

    private fun initObservables(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch() {
                    viewModel.stockList.collect{ list ->
                        adapter.list = list
                    }
                }
            }
        }
    }

}