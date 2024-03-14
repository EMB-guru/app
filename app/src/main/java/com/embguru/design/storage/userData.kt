package com.embguru.design.storage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.denzcoskun.imageslider.models.SlideModel
import com.embguru.design.*
import com.embguru.design.helper.getDatediff
import com.embguru.design.model.ItemsViewModel
import com.embguru.design.model.folderViewModel
import com.embguru.design.model.requirementViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class userData private constructor() {
    var email: String? = null
    var mobileNumber: String? = null
    var role: String? = null
    var uid: String? = null
    var databaseRef: DatabaseReference? = null
    var skill1: ArrayList<String>? = null
    var skill2: ArrayList<String>? = null

    companion object {
        @Volatile
        private var instance: userData? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: userData().also { instance = it }
            }

        fun isUserLogin(context: Context, activity: Activity): Boolean {
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val APPDATA = appData.getInstance()
            val CategoryData = category.getInstance()
            val FolderData = folderData.getInstance()
            val RequirementData = requirementData.getInstance()
            val DateCal = getDatediff()
            var onTimeFlag = true

            return if (currentUser != null) {
                val uid = currentUser.uid
                val userData = userData.getInstance()
                userData.email = currentUser.email
                userData.uid = uid
                userData.databaseRef =
                    FirebaseDatabase.getInstance().getReference("user").child(uid)
                val databaseRef = FirebaseDatabase.getInstance().getReference("appdata")
                val ref = FirebaseDatabase.getInstance().getReference("request")
                val skillRef = FirebaseDatabase.getInstance().getReference("skill")
                val query = ref.orderByChild("uid").equalTo(uid)

                val valueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild("mobileNumber"))
                            userData.mobileNumber = snapshot.child("mobileNumber").value.toString()
                        if (snapshot.hasChild("role"))
                            userData.role = snapshot.child("role").value.toString()
                        if (onTimeFlag) {
                            onTimeFlag = false
                            if (!snapshot.hasChild("role")) {
                                val changePage =
                                    Intent(context, RoleSelection::class.java)
                                changePage.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(changePage)
                            } else if (!snapshot.hasChild("name")) {
                                val changePage =
                                    Intent(context, CreateAccount::class.java)
                                changePage.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(changePage)
                            } else if (!snapshot.hasChild("skill")) {
                                val changePage = Intent(context, IntrestPage::class.java)
                                changePage.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(changePage)
                            } else {
                                val changePage = Intent(context, HomePage::class.java)
                                changePage.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(changePage)
                            }
                            activity.finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Code to handle a read error goes here
                    }
                }

                val appDataValueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.e("snapshot", snapshot.value.toString())
                        if (snapshot.hasChild("poster")) {
                            val imageList = ArrayList<SlideModel>()
                            for (userSnapshot in snapshot.child("poster").children) {
                                if (userSnapshot.hasChild("image") && userSnapshot.hasChild("name"))
                                    imageList.add(
                                        SlideModel(
                                            userSnapshot.child("image").value.toString(),
                                            userSnapshot.child("name").value.toString()
                                        )
                                    )
                            }
                            APPDATA.setData(imageList)
                        }

                        if (snapshot.hasChild("category")) {
                            val categoryList = ArrayList<ItemsViewModel>()
                            for (userSnapshot in snapshot.child("category").children) {
                                if (userSnapshot.hasChild("image") && userSnapshot.hasChild("name"))
                                    categoryList.add(
                                        ItemsViewModel(
                                            userSnapshot.child("image").value.toString(),
                                            userSnapshot.child("name").value.toString()
                                        )
                                    )
                            }
                            CategoryData.setData(categoryList)
                        }

                        if (snapshot.hasChild("folder")) {
                            val folderList = ArrayList<folderViewModel>()
                            for (userSnapshot in snapshot.child("folder").children) {
                                if (userSnapshot.hasChild("cname")
                                    && userSnapshot.hasChild("db")
                                    && userSnapshot.hasChild("fname")
                                    && userSnapshot.hasChild("furl")
                                    && userSnapshot.hasChild("time")
                                    && userSnapshot.hasChild("type")
                                )
                                    folderList.add(
                                        folderViewModel(
                                            userSnapshot.child("cname").value.toString(),
                                            userSnapshot.child("fname").value.toString(),
                                            userSnapshot.child("furl").value.toString(),
                                            userSnapshot.child("db").value.toString(),
                                            userSnapshot.child("time").value.toString(),
                                            userSnapshot.child("type").value.toString()
                                        )
                                    )
                            }
                            FolderData.setData(folderList)
                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Code to handle a read error goes here
                    }
                }

                val appRequestValueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.childrenCount > 0) {
                            val requirementList = ArrayList<requirementViewModel>()
                            for (userSnapshot in snapshot.children.reversed()) {
                                if (userSnapshot.hasChild("date")
                                    && userSnapshot.hasChild("mobileNumber")
                                    && userSnapshot.hasChild("name")
                                    && userSnapshot.hasChild("status")
                                )
                                    requirementList.add(
                                        requirementViewModel(
                                            "ID : ${userSnapshot.key}",
                                            DateCal.formatDate(
                                                userSnapshot.child("date").value.toString()
                                            ),
                                            "Requirement of ${userSnapshot.child("name").value.toString()}",
                                            userSnapshot.child("status").value.toString()
                                        )
                                    )

                            }
                            RequirementData.setData(requirementList)
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Code to handle a read error goes here
                    }
                }

                val appSkillValueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.hasChild("2") && snapshot.hasChild("1")) {
                            val skillList1 = ArrayList<String>()
                            for (userSnapshot in snapshot.child("1").children.reversed()) {
                                skillList1.add(userSnapshot.value.toString())

                            }
                            userData.skill1 = skillList1

                            val skillList2 = ArrayList<String>()
                            for (userSnapshot in snapshot.child("2").children.reversed()) {
                                skillList2.add(userSnapshot.value.toString())

                            }
                            userData.skill2 = skillList2
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Code to handle a read error goes here
                    }
                }

                userData.databaseRef?.addValueEventListener(valueEventListener)
                skillRef.addValueEventListener(appSkillValueEventListener)

                databaseRef.addValueEventListener(appDataValueEventListener)
                query.addValueEventListener(appRequestValueEventListener)
                true
            } else {
                false
            }

        }
    }

}
