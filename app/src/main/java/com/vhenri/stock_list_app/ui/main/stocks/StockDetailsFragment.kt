package com.vhenri.stock_list_app.ui.main.stocks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vhenri.stock_list_app.databinding.FragmentStockDetailBinding
import com.vhenri.stock_list_app.helpers.convertCentsToDollarString
import com.vhenri.stock_list_app.models.CompanyProfile
import com.vhenri.stock_list_app.ui.main.MainActivity
import com.vhenri.stock_list_app.ui.main.MainViewModel
import com.vhenri.stock_list_app.ui.main.UiState
import kotlinx.coroutines.launch
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
        viewModel.clearNavigationState()
        viewModel.getCompanyProfileBySymbol()
        initObservables()
        initBindings()
    }

    private fun initObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch() {
                    viewModel.uiState.collect {
                        if (it is UiState.DetailUiState){
                            if (it.companyProfile != null) {
                                bindCompanyProfile(it.companyProfile)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initBindings() {
        val stock = viewModel.getSelectedStock()
        binding.tickerName.text = "${stock?.ticker} - ${stock?.name}"
        binding.currencyPrice.text = "${stock?.currentPriceCents?.convertCentsToDollarString()} (${stock?.currency})"
        if (stock?.quantity != null){
            binding.quantity.isVisible = true
            binding.quantity.text = "Quantity - ${stock.quantity}"
        } else {
            binding.quantity.isVisible = false
        }
    }

    private fun bindCompanyProfile(profile: CompanyProfile) {
        binding.detailsCountry.text = "Country - ${profile.country}"
        binding.detailsExchange.text = "Exchange - ${profile.exchange}"
        binding.detailsIpo.text = "IPO Date - ${profile.ipoDate}"
        binding.detailsIndustry.text = "Industry - ${profile.finnhubIndustry}"

    }

}