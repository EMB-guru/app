package com.embguru.design.storage

import com.embguru.design.model.folderViewModel
import com.embguru.design.model.requirementViewModel
import java.util.*
import kotlin.collections.ArrayList

class requirementData: Observable() {
    var requirementList:  ArrayList<requirementViewModel>? = null

    companion object {
        @Volatile private var instance: requirementData? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: requirementData().also { instance = it }
            }
    }

    fun setData(newData: ArrayList<requirementViewModel>) {
        requirementList = newData

        // Notify the observers that the data has changed
        setChanged()
        notifyObservers()
    }

}