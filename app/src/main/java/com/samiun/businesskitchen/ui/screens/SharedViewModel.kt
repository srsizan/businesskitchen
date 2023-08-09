package com.samiun.businesskitchen.ui.screens

import android.content.ClipData.Item
import android.content.Context
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

    fun addCake(items: Items, context: Context) {
        if(currentData ==null){
            currentData = ItemRecords()
        }
        cakeList = currentData!!.data.cakeItems.toMutableList()
        cakeList.add(items)
        currentData!!.data.cakeItems = cakeList
        settingsRepository.setListToSharedPref(currentData!!)
    }
    fun getCake(): ItemRecords? {
        return settingsRepository.getListFromSharedPref()
    }
}