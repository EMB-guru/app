package com.embguru.design

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.embguru.design.storage.*
import com.embguru.design.storage.userData.Companion.isUserLogin
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val background: ImageView = findViewById(R.id.background)
        val logo: ImageView = findViewById(R.id.logo)
        val animFadeIn: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        val animFadeIn2: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        FirebaseApp.initializeApp(this)
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

         if(!userData.isUserLogin(applicationContext,this))
         {
             val changePage = Intent(this, Feature_Screen_1::class.java)
             startActivity(changePage)
             finish()
         }
        }, 1200)


    }
}