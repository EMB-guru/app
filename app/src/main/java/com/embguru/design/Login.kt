package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout

class Login : AppCompatActivity() {
    private var mobileNumber: EditText? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        define();
    }

    private fun define(){
        mobileNumber=findViewById(R.id.mobileNumber)
    }

    private fun goOnNextPage(){
        val changePage = Intent(this, Otp::class.java)
        startActivity(changePage)
    }

    /** on button Click Handler */
    fun onSendOtpClick(view: View) {
        goOnNextPage()
    }

}