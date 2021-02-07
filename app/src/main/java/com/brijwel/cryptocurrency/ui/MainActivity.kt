package com.brijwel.cryptocurrency.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.brijwel.cryptocurrency.R
import com.brijwel.cryptocurrency.bindingadapter.BindingAdapter
import com.brijwel.cryptocurrency.databinding.ActivityMainBinding
import com.brijwel.cryptocurrency.util.openWebPage
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val SEARCH_QUERY = "search_query"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CryptoCurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        adapter = CryptoCurrencyAdapter() {
            openWebPage(it.explorer)
        }
        binding.cryptoCurrencyRecyclerView.setHasFixedSize(true)
        binding.cryptoCurrencyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                RecyclerView.VERTICAL
            )
        )
        binding.cryptoCurrencyRecyclerView.adapter = adapter

        viewModel.cryptoCurrencyLiveData.observe(this) { cryptoData ->
            adapter.submitList(cryptoData)
        }

        viewModel.globalMarketCapLiveData.observe(this) { binding.globalMarketCapPrice = it }



        viewModel.loadCryptoCurrencyData()
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadCryptoCurrencyData(true)
        }

        viewModel.isLoading.observe(this) { binding.swipeRefresh.isRefreshing = it }


        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                viewModel.getCryptoCurrencyData(editable.toString())
                binding.cryptoCurrencyRecyclerView.scrollToPosition(0)
            }

        })



        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val query = savedInstanceState.getString(SEARCH_QUERY)
        binding.search.setText(query)
        binding.search.setSelection(query?.length ?: 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY, binding.search.text.toString())
    }
}