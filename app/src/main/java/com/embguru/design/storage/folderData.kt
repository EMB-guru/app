package com.embguru.design.storage

import com.embguru.design.model.ItemsViewModel
import com.embguru.design.model.folderViewModel
import java.util.*
import kotlin.collections.ArrayList

class folderData : Observable() {
    var folderList:  ArrayList<folderViewModel>? = null

    companion object {
        @Volatile private var instance: folderData? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: folderData().also { instance = it }
            }
    }

    fun setData(newData: ArrayList<folderViewModel>) {
        folderList = newData

        // Notify the observers that the data has changed
        setChanged()
        notifyObservers()
    }

}