package com.vhenri.stock_list_app.ui.main.stocks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vhenri.stock_list_app.databinding.FragmentStockDetailBinding
import com.vhenri.stock_list_app.ui.main.MainActivity
import com.vhenri.stock_list_app.ui.main.MainViewModel
import javax.inject.Inject

class StockDetailsFragment : Fragment() {

    lateinit var binding: FragmentStockDetailBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        viewModel = (requireActivity() as MainActivity).viewModel
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
    }

    fun initBindings() {
        //TODO
    }

}