package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class Otp : AppCompatActivity() {
    private var Timer: TextView? =null
    private var resendText: TextView? =null
    private var isclickable=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        define()
        startTimer()

        resendText?.setOnClickListener {
            if(isclickable)
            {
                isclickable=false
                startTimer()
            }

        }
    }

    private fun define(){
        Timer=findViewById(R.id.Timer)
        resendText=findViewById(R.id.resendText)

    }


    private fun startTimer(){
        resendText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.teal_200))
        val timer = object: CountDownTimer(61000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("Time",millisUntilFinished.toString())
                Timer?.text = "${millisUntilFinished/1000} S"
            }

            override fun onFinish() {
                isclickable=true;
                resendText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.teal_900))
            }
        }
        timer.start()
    }


    private fun goOnNextPage(){
        val changePage = Intent(this, WelcomeScreen::class.java)
        startActivity(changePage)
    }

    /** on button Click Handler */
    fun onSendOtpClick(view: View) {
        goOnNextPage()
    }
}