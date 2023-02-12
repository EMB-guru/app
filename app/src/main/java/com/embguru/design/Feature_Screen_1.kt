package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class Feature_Screen_1 : AppCompatActivity() {
    private var page=1
    private var dot1:LinearLayout? =null
    private var dot2:LinearLayout? =null
    private var dot3:LinearLayout? =null
    private var infoHeading:TextView? =null
    private val titleArray = arrayOf("Get 100% Free Design","Get new designs every week","New Updated Design")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_screen1)
        define();
    }

    private fun define(){
        dot1=findViewById(R.id.dot_1)
        dot2=findViewById(R.id.dot_2)
        dot3=findViewById(R.id.dot_3)
        infoHeading=findViewById(R.id.infoHeading)
    }

    private fun setPage(index: Int){
        val animFadeIn : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.fade_in_fast)
        dot1?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.gray_dot)
        dot2?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.gray_dot)
        dot3?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.gray_dot)
        infoHeading?.startAnimation(animFadeIn)
        when (index) {
            1 -> {
                dot1?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_dot)
                infoHeading?.text = titleArray[0]
            }
            2 -> {
                dot2?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_dot)
                infoHeading?.text = titleArray[1]
            }
            3 -> {
                dot3?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_dot)
                infoHeading?.text = titleArray[2]
            }
            else -> {
                goOnNextPage()
            }
        }
    }

    private fun goOnNextPage(){
        val changePage = Intent(this, Login::class.java)
        startActivity(changePage)
    }

    /** on button Click Handler */
    fun onSkipClick(view: View) {
        goOnNextPage()
    }

    fun onNextClick(view: View) {
        page+=1
        Log.e("Page",page.toString())
        setPage(page)
    }
}