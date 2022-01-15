package com.example.shoping.presentation

import androidx.lifecycle.ViewModel
import com.example.shoping.data.ShopListRepositoryImpl
import com.example.shoping.domain.*
import java.lang.Exception

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItemUseCase(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parsName(inputName)
        val count = parseCount(inputCount)
        val fieldValidate = validateInput(name, count)
        if (fieldValidate) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parsName(inputName)
        val count = parseCount(inputCount)
        val fieldValidate = validateInput(name, count)
        if (fieldValidate) {
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
        }
    }

    private fun parsName(inputName: String?): String {
        val resault = inputName?.trim() ?: ""
        return resault
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var rezault = true
        if (name.isBlank()){
            rezault = false
        }
        if (count <= 0) {
            rezault =  false
        }
        return rezault
    }
}