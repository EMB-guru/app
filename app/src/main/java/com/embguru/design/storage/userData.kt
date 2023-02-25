package com.embguru.design.storage

import com.embguru.design.model.ItemsViewModel
import com.google.firebase.database.DatabaseReference
import java.util.*
import kotlin.collections.ArrayList

class userData private constructor() {
    var phoneNumber: String? = null
    var uid : String? = null
    var databaseRef : DatabaseReference? = null

    companion object {
        @Volatile private var instance: userData? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: userData().also { instance = it }
            }
    }

}
