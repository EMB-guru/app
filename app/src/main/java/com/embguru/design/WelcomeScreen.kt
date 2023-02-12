package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class WelcomeScreen : AppCompatActivity() {
    private var logo: ImageView? =null
    private var dot: ImageView? =null
    private var avtar: ImageView? =null
    private var message: TextView? =null
    private var createAccountButton: LinearLayout? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        define();
        StartAnimation();
    }
    private fun define(){
        logo=findViewById(R.id.logo)
        dot=findViewById(R.id.dot)
        avtar=findViewById(R.id.avtar)
        message=findViewById(R.id.message)
        createAccountButton=findViewById(R.id.createAccountButton)

        dot?.visibility = View.GONE;
        avtar?.visibility = View.GONE;
        message?.visibility = View.GONE;
        createAccountButton?.visibility = View.GONE;
    }

    private fun StartAnimation(){


        Handler(Looper.getMainLooper()).postDelayed({
            val animFadeIn : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in)
            avtar?.visibility = View.VISIBLE
            avtar?.startAnimation(animFadeIn)
        }, 500)

        Handler(Looper.getMainLooper()).postDelayed({
            val animFadeIn : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in)
            dot?.visibility = View.VISIBLE
            dot?.startAnimation(animFadeIn)
        }, 800)

        Handler(Looper.getMainLooper()).postDelayed({
            val animFadeIn : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in)
            message?.text = "Hi"
            message?.visibility = View.VISIBLE
            message?.startAnimation(animFadeIn)
        }, 2000)
        Handler(Looper.getMainLooper()).postDelayed({
            val animFadeIn : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in)
            message?.text = "You are new in EMB Guru Design Family Welcome ! "
            message?.startAnimation(animFadeIn)
        }, 5000)

        Handler(Looper.getMainLooper()).postDelayed({
            val animFadeIn : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in)
            createAccountButton?.visibility = View.VISIBLE
            createAccountButton?.startAnimation(animFadeIn)

        }, 8000)
    }

    private fun goOnNextPage(){
        val changePage = Intent(this, RoleSelection::class.java)
        startActivity(changePage)
    }

    /** on button Click Handler */
    fun onCreateAccountClick(view: View) {
        goOnNextPage()
    }

}