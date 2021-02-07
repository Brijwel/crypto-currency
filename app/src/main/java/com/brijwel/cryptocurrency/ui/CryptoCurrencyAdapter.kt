package com.brijwel.cryptocurrency.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brijwel.cryptocurrency.R
import com.brijwel.cryptocurrency.model.CryptoCurrency
import com.brijwel.cryptocurrency.databinding.ItemCryptoCurrencyBinding

/**
 * Created by Brijwel on 06-02-2021.
 */
class CryptoCurrencyAdapter(private val onclick: (cryptoCurrency: CryptoCurrency) -> Unit) :
    ListAdapter<CryptoCurrency, CryptoCurrencyAdapter.CryptoCurrencyViewHolder>(CURRENCY_COMPARATOR) {

    inner class CryptoCurrencyViewHolder(private val binding: ItemCryptoCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    getItem(adapterPosition)?.let {
                        onclick(it)
                    }
                }

            }
        }

        fun bind(cryptoCurrency: CryptoCurrency) {
            binding.apply {
                setCryptoCurrency(cryptoCurrency)
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoCurrencyViewHolder {
        val binding = DataBindingUtil.inflate<ItemCryptoCurrencyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_crypto_currency, parent, false
        )
        return CryptoCurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoCurrencyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val CURRENCY_COMPARATOR = object : DiffUtil.ItemCallback<CryptoCurrency>() {
            override fun areItemsTheSame(
                oldItem: CryptoCurrency,
                newItem: CryptoCurrency
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CryptoCurrency,
                newItem: CryptoCurrency
            ) = oldItem == newItem

        }
    }
}