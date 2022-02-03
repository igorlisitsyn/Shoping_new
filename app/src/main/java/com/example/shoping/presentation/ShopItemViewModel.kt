package com.example.shoping.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoping.data.ShopListRepositoryImpl
import com.example.shoping.domain.*
import java.lang.Exception

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItemUseCase(shopItemId)
        _shopItem.value = item
    }

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parsName(inputName)
        val count = parseCount(inputCount)
        val fieldValidate = validateInput(name, count)
        if (fieldValidate) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            _shouldCloseScreen.value = Unit
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parsName(inputName)
        val count = parseCount(inputCount)
        val fieldValidate = validateInput(name, count)
        if (fieldValidate) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                _shouldCloseScreen.value = Unit
            }

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
            _errorInputName.value = true
            rezault = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            rezault =  false
        }
        return rezault
    }
    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }
}