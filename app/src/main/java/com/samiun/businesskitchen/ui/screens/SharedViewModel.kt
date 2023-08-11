package com.samiun.businesskitchen.ui.screens

import androidx.lifecycle.ViewModel
import com.samiun.businesskitchen.data.model.ItemRecords
import com.samiun.businesskitchen.data.model.Items
import com.samiun.businesskitchen.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    var currentData = settingsRepository.getListFromSharedPref()
    var cakeList = mutableListOf<Items>()
    var consumerGoodList = mutableListOf<Items>()
    var miscList = mutableListOf<Items>()
    var fastFoodList = mutableListOf<Items>()
    var selectedItemIndex = -1

    var selectedItem = Items()
    var currentScreen = ""


    fun addItem(items: Items) {
        if (currentData == null) {
            currentData = ItemRecords()
        }
        if(selectedItemIndex<0) {
            when (items.category) {
                Screen.CakeScreen.route -> {
                    cakeList = currentData!!.data.cakeItems.toMutableList()
                    cakeList.add(items)
                    currentData!!.data.cakeItems = cakeList
                    settingsRepository.setListToSharedPref(currentData!!)
                }

                Screen.ConsumerGoodsScreen.route -> {
                    consumerGoodList = currentData!!.data.consumerGoodItems.toMutableList()
                    consumerGoodList.add(items)
                    currentData!!.data.consumerGoodItems = consumerGoodList
                    settingsRepository.setListToSharedPref(currentData!!)
                }

                Screen.FastFoodScreen.route -> {
                    fastFoodList = currentData!!.data.fastFoodItems.toMutableList()
                    fastFoodList.add(items)
                    currentData!!.data.fastFoodItems = fastFoodList
                    settingsRepository.setListToSharedPref(currentData!!)
                }

                else -> {
                    miscList = currentData!!.data.miscItems.toMutableList()
                    miscList.add(items)
                    currentData!!.data.miscItems = miscList
                    settingsRepository.setListToSharedPref(currentData!!)
                }
            }
        }
        else{
            when (items.category) {
                Screen.CakeScreen.route -> {
                    currentData!!.data.cakeItems[selectedItemIndex].apply {
                        this.name = items.name
                        this.quantity = items.quantity
                        this.image = items.image
                        this.category = items.category
                       this.maxStock = items.maxStock
                        this.maxUsage = items.maxUsage
                    }
                    settingsRepository.setListToSharedPref(currentData!!)
                }

                Screen.ConsumerGoodsScreen.route -> {
                    currentData!!.data.consumerGoodItems[selectedItemIndex].apply {
                        this.name = items.name
                        this.quantity = items.quantity
                        this.image = items.image
                        this.category = items.category
                        this.maxStock = items.maxStock
                        this.maxUsage = items.maxUsage
                    }
                    settingsRepository.setListToSharedPref(currentData!!)
                }

                Screen.FastFoodScreen.route -> {
                    currentData!!.data.fastFoodItems[selectedItemIndex].apply {
                        this.name = items.name
                        this.quantity = items.quantity
                        this.image = items.image
                        this.category = items.category
                        this.maxStock = items.maxStock
                        this.maxUsage = items.maxUsage
                    }
                    settingsRepository.setListToSharedPref(currentData!!)
                }

                else -> {
                    currentData!!.data.miscItems[selectedItemIndex].apply {
                        this.name = items.name
                        this.quantity = items.quantity
                        this.image = items.image
                        this.category = items.category
                        this.maxStock = items.maxStock
                        this.maxUsage = items.maxUsage
                    }
                    settingsRepository.setListToSharedPref(currentData!!)
                }
            }
        }
    }

    fun addCake(items: Items) {
        if (currentData == null) {
            currentData = ItemRecords()
        }
        cakeList = currentData!!.data.cakeItems.toMutableList()
        cakeList.add(items)
        currentData!!.data.cakeItems = cakeList
        settingsRepository.setListToSharedPref(currentData!!)
    }

    fun addConsumerGood(items: Items) {
        if (currentData == null) {
            currentData = ItemRecords()
        }
        consumerGoodList = currentData!!.data.consumerGoodItems.toMutableList()
        consumerGoodList.add(items)
        currentData!!.data.consumerGoodItems = consumerGoodList
        settingsRepository.setListToSharedPref(currentData!!)
    }

    fun addMisc(items: Items) {
        if (currentData == null) {
            currentData = ItemRecords()
        }
        miscList = currentData!!.data.miscItems.toMutableList()
        miscList.add(items)
        currentData!!.data.miscItems = miscList
        settingsRepository.setListToSharedPref(currentData!!)
    }

    fun addFastFood(items: Items) {
        if (currentData == null) {
            currentData = ItemRecords()
        }
        fastFoodList = currentData!!.data.fastFoodItems.toMutableList()
        fastFoodList.add(items)
        currentData!!.data.fastFoodItems = fastFoodList
        settingsRepository.setListToSharedPref(currentData!!)
    }

    fun addItemsFromControlPanel(listOfPair: List<Pair<String, String>>) {
        settingsRepository.setControlPanelSharedPref(listOfPair)
    }

    fun getControlPanelList(): List<Pair<String, String>>? {
        return settingsRepository.getControlPanelFromSharedPref()
    }

    fun getItems(): ItemRecords? {
        return settingsRepository.getListFromSharedPref()
    }
}