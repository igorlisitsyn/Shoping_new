package com.example.shoping.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoping.data.ShopListRepositoryImpl
import com.example.shoping.domain.*

class MainViewModel: ViewModel() {

    private val shopListRepository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(shopListRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopListRepository)

    val shopList = getShopListUseCase.getShopList()

    /*fun getShopList(){
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }*/

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
        //getShopList()
    }

    fun changeEnableState(shopItem: ShopItem){
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
        //getShopList()
    }
}