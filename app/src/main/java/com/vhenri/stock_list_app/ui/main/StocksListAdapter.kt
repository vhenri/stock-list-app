package com.vhenri.stock_list_app.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vhenri.stock_list_app.databinding.StockCellBinding
import com.vhenri.stock_list_app.models.Stock

class StocksListAdapter : RecyclerView.Adapter<StockCellVH>() {
    var list: List<Stock> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockCellVH {
        return StockCellVH(
            StockCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: StockCellVH, position: Int) = holder.bind(list[position])
}

class StockCellVH(private val binding: StockCellBinding) : ViewHolder(binding.root) {
    fun bind(stock: Stock) {
        binding.tickerName.text = "${stock.ticker} - ${stock.name}"
        binding.currencyPrice.text = "${stock.currency} - ${stock.currentPriceCents}"
        if (stock.quantity != null){
            binding.quantity.isVisible = true
            binding.quantity.text = "Quantity - ${stock.quantity}"
        } else {
            binding.quantity.isVisible = false
        }

    }
}