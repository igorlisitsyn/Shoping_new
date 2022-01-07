package com.example.shoping.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoping.R
import com.example.shoping.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            Log.d("MainActivityTest",it.toString())
            shopListAdapter.shoplist = it
        }

    }
    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()

        // определяем размерность пула (кэша) для вызываемых ViewHolder
        with(rvShopList) {
           // adapter = ShopListAdapter()
            adapter = shopListAdapter
            // определяем размерность пула (кэша) для вызываемых ViewHolder
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.MAX_POOL_SIZE)
        }

        //это рабочий вариант
        /*shopListAdapter.onShopItemLongClick = object : ShopListAdapter.OnShopItemLongClickListener {
            override fun onSopItemLongClick(shopItem: ShopItem) {
                viewModel.changeEnableState(shopItem)
            }

        }*/

        // это вариант через лямбду
        shopListAdapter.onShopItemLongClick = {
            viewModel.changeEnableState(it)
        }
    }
}