package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.denzcoskun.imageslider.models.SlideModel
import com.embguru.design.helper.getDatediff
import com.embguru.design.model.ItemsViewModel
import com.embguru.design.model.folderViewModel
import com.embguru.design.model.requirementViewModel
import com.embguru.design.storage.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    var databaseRef: DatabaseReference? = null
    val DateCal = getDatediff();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val background: ImageView = findViewById(R.id.background)
        val logo: ImageView = findViewById(R.id.logo)
        var onTimeFlag = true
        val animFadeIn: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        val animFadeIn2: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)

        FirebaseApp.initializeApp(this)


        val APPDATA = appData.getInstance()
        val CategoryData = category.getInstance()
        val FolderData = folderData.getInstance()
        val RequirementData = requirementData.getInstance()



        background.visibility = View.GONE
        logo.visibility = View.GONE
        background.startAnimation(animFadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            background.visibility = View.VISIBLE
            background.startAnimation(animFadeIn)
        }, 100)

        Handler(Looper.getMainLooper()).postDelayed({
            logo.visibility = View.VISIBLE
            logo.startAnimation(animFadeIn2)
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({

            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser

            if (currentUser != null) {
                val uid = currentUser.uid
                val phoneNumber = currentUser.phoneNumber
                val userData = userData.getInstance()
                userData.phoneNumber = phoneNumber
                userData.uid = uid
                userData.databaseRef = phoneNumber?.let {
                    FirebaseDatabase.getInstance().getReference("user").child(it)
                }
                databaseRef = FirebaseDatabase.getInstance().getReference("appdata")
                val ref = FirebaseDatabase.getInstance().getReference("request")
                val query = ref.orderByChild("mobileNumber").equalTo(phoneNumber)

                val valueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (onTimeFlag) {
                            onTimeFlag = false
                            if (!snapshot.hasChild("role")) {
                                val changePage =
                                    Intent(applicationContext, RoleSelection::class.java)
                                startActivity(changePage)
                            } else if (!snapshot.hasChild("name")) {
                                val changePage =
                                    Intent(applicationContext, CreateAccount::class.java)
                                startActivity(changePage)
                            } else if (!snapshot.hasChild("skill")) {
                                val changePage = Intent(applicationContext, IntrestPage::class.java)
                                startActivity(changePage)
                            } else {
                                val changePage = Intent(applicationContext, HomePage::class.java)
                                startActivity(changePage)
                            }
                            finish()
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
                                imageList.add(
                                    SlideModel(
                                        userSnapshot.value.toString(),
                                        userSnapshot.key
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

                userData.databaseRef?.addValueEventListener(valueEventListener)
                databaseRef?.addValueEventListener(appDataValueEventListener)
                query.addValueEventListener(appRequestValueEventListener)

            } else {
                val changePage = Intent(this, Feature_Screen_1::class.java)
                startActivity(changePage)
                finish()
            }

        }, 3000)


    }
}