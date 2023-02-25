package com.embguru.design.storage

import com.denzcoskun.imageslider.models.SlideModel
import com.embguru.design.model.ItemsViewModel
import java.util.*
import kotlin.collections.ArrayList

class appData: Observable() {
    var Poster:  ArrayList<SlideModel>? = null

    companion object {
        @Volatile private var instance: appData? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: appData().also { instance = it }
            }
    }

    fun setData(newData: ArrayList<SlideModel>) {
        Poster = newData

        // Notify the observers that the data has changed
        setChanged()
        notifyObservers()
    }

}