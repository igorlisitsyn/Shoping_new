package com.example.shoping.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun getShopItemUseCase(shopItemId: Int): ShopItem

    fun getShopList(): List<ShopItem>

}