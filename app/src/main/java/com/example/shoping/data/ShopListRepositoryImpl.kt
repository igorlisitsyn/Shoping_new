package com.example.shoping.data

import com.example.shoping.domain.ShopItem
import com.example.shoping.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var countId = 0

    init {
        for (i in 0 until 10){
            val item = ShopItem("Name $i",i,true)
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if ( shopItem.id == ShopItem.UNDEFIND_ID){
            shopItem.id = countId
            countId++
        }
        shopList.add(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItemUseCase(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)

    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getShopItemUseCase(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?:
        throw RuntimeException("Element with id -> $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}