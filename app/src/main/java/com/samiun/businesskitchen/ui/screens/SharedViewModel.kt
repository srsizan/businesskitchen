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

    var currentList = settingsRepository.getControlPanelFromSharedPref()
    var selectedItems = mutableListOf<Pair<String,Int>>()


    fun addCake(items: Items) {
        if(currentData ==null){
            currentData = ItemRecords()
        }
        cakeList = currentData!!.data.cakeItems.toMutableList()
        cakeList.add(items)
        currentData!!.data.cakeItems = cakeList
        settingsRepository.setListToSharedPref(currentData!!)
    }
    fun addConsumerGood(items: Items) {
        if(currentData ==null){
            currentData = ItemRecords()
        }
        consumerGoodList = currentData!!.data.consumerGoodItems.toMutableList()
        consumerGoodList.add(items)
        currentData!!.data.consumerGoodItems = consumerGoodList
        settingsRepository.setListToSharedPref(currentData!!)
    }
    fun addMisc(items: Items) {
        if(currentData ==null){
            currentData = ItemRecords()
        }
        miscList = currentData!!.data.miscItems.toMutableList()
        miscList.add(items)
        currentData!!.data.miscItems = miscList
        settingsRepository.setListToSharedPref(currentData!!)
    }
    fun addFastFood(items: Items) {
        if(currentData ==null){
            currentData = ItemRecords()
        }
        fastFoodList = currentData!!.data.fastFoodItems.toMutableList()
        fastFoodList.add(items)
        currentData!!.data.fastFoodItems = fastFoodList
        settingsRepository.setListToSharedPref(currentData!!)
    }

    fun addItemsFromControlPanel(listOfPair: List<Pair<String,Int>>) {
        settingsRepository.setControlPanelSharedPref(listOfPair)
    }
    fun getControlPanelList(): List<Pair<String, Int>>? {
       return settingsRepository.getControlPanelFromSharedPref()
    }
    fun getItems(): ItemRecords? {
        return settingsRepository.getListFromSharedPref()
    }
}