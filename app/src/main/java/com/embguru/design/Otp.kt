package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.chaos.view.PinView
import com.denzcoskun.imageslider.models.SlideModel
import com.embguru.design.helper.getDatediff
import com.embguru.design.model.ItemsViewModel
import com.embguru.design.model.folderViewModel
import com.embguru.design.model.requirementViewModel
import com.embguru.design.storage.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import java.util.concurrent.TimeUnit
import kotlin.math.log

class Otp : AppCompatActivity() {
    private var Timer: TextView? = null
    private var resendText: TextView? = null
    private var pin_view: PinView? = null
    private var sendBtn: LinearLayout? = null
    private var Progress: ProgressBar? = null
    private var buttonText: TextView? = null
    private var isclickable = false
    var auth: FirebaseAuth? = null
    var varificationId: String? = null
    var databaseRef: DatabaseReference? = null
    var onTimeFlag = true
    val APPDATA = appData.getInstance()
    val CategoryData = category.getInstance()
    val FolderData = folderData.getInstance()
    val RequirementData = requirementData.getInstance()
    val DateCal = getDatediff();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        define()
        startTimer()

        resendText?.setOnClickListener {
            if (isclickable) {
                isclickable = false

                val options = auth?.let { it1 ->
                    PhoneAuthOptions.newBuilder(it1)
                        .setPhoneNumber(intent.getStringExtra("PhoneNumber").toString())
                        .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(object :
                            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                // Automatically sign in the user if the verification is successful
                                auth?.signInWithCredential(credential)

                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                // Handle the error
                            }

                            override fun onCodeSent(
                                verificationId: String, token: PhoneAuthProvider.ForceResendingToken
                            ) {
                                varificationId = verificationId
                            }
                        }).build()
                }
                if (options != null) {
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }
                startTimer()
            }

        }
    }

    private fun define() {
        Timer = findViewById(R.id.Timer)
        resendText = findViewById(R.id.resendText)
        pin_view = findViewById(R.id.pin_view)
        auth = FirebaseAuth.getInstance()
        sendBtn = findViewById(R.id.sendBtn)
        Progress = findViewById(R.id.Progress)
        buttonText = findViewById(R.id.buttonText)
        Progress?.visibility = View.GONE
        varificationId = intent.getStringExtra("verificationId").toString()

    }


    private fun startTimer() {
        resendText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.teal_200
            )
        )
        val timer = object : CountDownTimer(61000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("Time", millisUntilFinished.toString())
                Timer?.text = "${millisUntilFinished / 1000} S"
            }

            override fun onFinish() {
                isclickable = true;
                resendText?.setTextColor(
                    AppCompatResources.getColorStateList(
                        applicationContext,
                        R.color.teal_900
                    )
                )
            }
        }
        timer.start()
    }


    private fun goOnNextPage() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            val phoneNumber = currentUser.phoneNumber
            val userData = userData.getInstance()
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
                            println("Image ${userSnapshot.child("image").value.toString()}");
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
                                && userSnapshot.hasChild("requirement")
                            )
                                requirementList.add(
                                    requirementViewModel(
                                        "ID : ${userSnapshot.key}",
                                        DateCal.formatDate(
                                            userSnapshot.child("date").value.toString()
                                        ),
                                        "Requirement of ${userSnapshot.child("name").value.toString()}",
                                        userSnapshot.child("status").value.toString(),
                                        userSnapshot.child("requirement").value.toString()
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

    }

    private fun startLoading() {
        Progress?.visibility = View.VISIBLE
        sendBtn?.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.disable_button_background)
        buttonText?.text = "Loading ...."
        buttonText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.teal_1000
            )
        )
    }

    private fun stopLoading() {
        Progress?.visibility = View.GONE
        sendBtn?.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.button_background)
        buttonText?.text = "Verify"
        buttonText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.white
            )
        )
    }

    /** on button Click Handler */
    fun onSendOtpClick(view: View) {
        startLoading()
        val otp = pin_view?.text.toString()// The user's one-time password
        val credential = varificationId?.let { PhoneAuthProvider.getCredential(it, otp) }
        if (credential != null) {
            auth?.signInWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    goOnNextPage()
                } else {
                    // Verification failed
                    Toast.makeText(
                        applicationContext,
                        "Your Otp is not correct please try again",
                        Toast.LENGTH_LONG
                    ).show()
                    stopLoading()
                }
            }
        } else {
            stopLoading()
        }


    }
}