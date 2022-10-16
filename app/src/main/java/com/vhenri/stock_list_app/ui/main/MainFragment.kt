package com.vhenri.stock_list_app.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vhenri.stock_list_app.databinding.FragmentMainBinding
import com.vhenri.stock_list_app.repo.ApiType
import javax.inject.Inject

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        viewModel = (requireActivity() as MainActivity).viewModel
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getStocksData(ApiType.PORTFOLIO)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

}