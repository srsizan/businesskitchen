package com.samiun.businesskitchen.repository

import android.content.SharedPreferences
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.samiun.businesskitchen.data.model.ItemRecords
import com.samiun.businesskitchen.util.Constants.ITEM_TOKEN
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsRepository @Inject constructor(private val prefs: SharedPreferences) {
    fun setListToSharedPref(itemRecords: ItemRecords) {
        prefs.edit().putString(ITEM_TOKEN, Gson().toJson(itemRecords)).apply()
    }
    fun getListFromSharedPref(): ItemRecords? {
        val listOfMyClassObject: Type = object : TypeToken<ItemRecords?>() {}.type
        return Gson().fromJson(prefs.getString(ITEM_TOKEN, ""), listOfMyClassObject)
    }
}