package com.embguru.design.storage

import com.embguru.design.model.ItemsViewModel
import java.util.*
import kotlin.collections.ArrayList

class category : Observable() {
    var categoryData:  ArrayList<ItemsViewModel>? = null

    companion object {
        @Volatile private var instance: category? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: category().also { instance = it }
            }
    }

    fun setData(newData: ArrayList<ItemsViewModel>) {
        categoryData = newData

        // Notify the observers that the data has changed
        setChanged()
        notifyObservers()
    }

    fun getData(): ArrayList<ItemsViewModel>? {
        return categoryData
    }
}
