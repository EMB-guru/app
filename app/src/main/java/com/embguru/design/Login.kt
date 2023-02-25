package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {
    private var mobileNumber: EditText? = null
    private var sendBtn: LinearLayout? = null
    private var Progress: ProgressBar? = null
    private var buttonText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        define()

    }

    private fun define() {
        mobileNumber = findViewById(R.id.mobileNumber)
        sendBtn = findViewById(R.id.sendBtn)
        Progress = findViewById(R.id.Progress)
        buttonText = findViewById(R.id.buttonText)
        Progress?.visibility = View.GONE

    }

    private fun startLoading() {
        Progress?.visibility = View.VISIBLE
        sendBtn?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.disable_button_background)
        buttonText?.text = "Loading ...."
        buttonText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.teal_1000))
    }

    private fun stopLoading() {
        Progress?.visibility = View.GONE
        sendBtn?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.button_background)
        buttonText?.text = "Send OTP"
        buttonText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.white))
    }

    /** on button Click Handler */
    fun onSendOtpClick(view: View) {
        startLoading()
        val auth = FirebaseAuth.getInstance()
        val phoneNumber = "+91${mobileNumber?.text}" // The user's phone number with country code
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Automatically sign in the user if the verification is successful
                    auth.signInWithCredential(credential)

                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Handle the error
                    stopLoading()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    val changePage = Intent(applicationContext, Otp::class.java)
                        .putExtra("verificationId", verificationId)
                        .putExtra("PhoneNumber", "+91${mobileNumber?.text}")

                    startActivity(changePage)
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }


}