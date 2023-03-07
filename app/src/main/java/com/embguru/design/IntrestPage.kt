package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import com.embguru.design.storage.userData
import java.util.*
import kotlin.collections.HashMap

class IntrestPage : AppCompatActivity() {
    private var skill1 = false
    private var skill2 = false
    private var skill3 = false
    private var skill4 = false
    private var skill5 = false
    private var skill6 = false

    private var skillOne: LinearLayout? = null
    private var skillTwo: LinearLayout? = null
    private var skillThree: LinearLayout? = null
    private var skillFour: LinearLayout? = null
    private var skillFive: LinearLayout? = null
    private var skillSix: LinearLayout? = null

    private var skillOneText: TextView? = null
    private var skillTwoText: TextView? = null
    private var skillThreeText: TextView? = null
    private var skillFourText: TextView? = null
    private var skillFiveText: TextView? = null
    private var skillSixText: TextView? = null

    private var userdata: userData? = userData.getInstance()

    private var sendBtn: LinearLayout? = null
    private var Progress: ProgressBar? = null
    private var buttonText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intrest_page)
        define()
        setInterestList()
    }

    private fun define() {
        skillOne = findViewById(R.id.skillOne)
        skillTwo = findViewById(R.id.skillTwo)
        skillThree = findViewById(R.id.skillThree)
        skillFour = findViewById(R.id.skillFour)
        skillFive = findViewById(R.id.skillFive)
        skillSix = findViewById(R.id.skillSix)
        skillOneText = findViewById(R.id.skillOneText)
        skillTwoText = findViewById(R.id.skillTwoText)
        skillThreeText = findViewById(R.id.skillThreeText)
        skillFourText = findViewById(R.id.skillFourText)
        skillFiveText = findViewById(R.id.skillFiveText)
        skillSixText = findViewById(R.id.skillSixText)


        sendBtn = findViewById(R.id.sendBtn)
        Progress = findViewById(R.id.Progress)
        buttonText = findViewById(R.id.buttonText)
        Progress?.visibility = View.GONE

        skillOne?.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
        skillOneText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.teal_900
            )
        )
        skillTwo?.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
        skillTwoText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.teal_900
            )
        )
        skillThree?.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
        skillThreeText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.teal_900
            )
        )
        skillFour?.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
        skillFourText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.teal_900
            )
        )
        skillFive?.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
        skillFiveText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.teal_900
            )
        )
        skillSix?.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
        skillSixText?.setTextColor(
            AppCompatResources.getColorStateList(
                applicationContext,
                R.color.teal_900
            )
        )
    }

    private fun goOnNextPage() {
        val changePage = Intent(this, HomePage::class.java)
        startActivity(changePage)
    }

    private fun setInterestList() {
        if (userdata?.role == "1") {
            skillOneText?.text = userdata?.skill1?.get(0)
            skillTwoText?.text= userdata?.skill1?.get(1)
            skillThreeText?.text= userdata?.skill1?.get(2)
            skillFourText?.text= userdata?.skill1?.get(3)
            skillFiveText?.text= userdata?.skill1?.get(4)
            skillSixText?.text= userdata?.skill1?.get(5)
        } else {
            skillOneText?.text = userdata?.skill2?.get(0)
            skillTwoText?.text= userdata?.skill2?.get(1)
            skillThreeText?.text= userdata?.skill2?.get(2)
            skillFourText?.text= userdata?.skill2?.get(3)
            skillFiveText?.text= userdata?.skill2?.get(4)
            skillSixText?.text= userdata?.skill2?.get(5)
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
    fun skillOneClick(view: View) {
        if (skill1) {
            skill1 = false
            skillOne?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
            skillOneText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.teal_900
                )
            )
        } else {
            skill1 = true
            skillOne?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.active_feild)
            skillOneText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.purple_200
                )
            )
        }

    }

    fun skillTwoClick(view: View) {
        if (skill2) {
            skill2 = false
            skillTwo?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
            skillTwoText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.teal_900
                )
            )
        } else {
            skill2 = true
            skillTwo?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.active_feild)
            skillTwoText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.purple_200
                )
            )
        }

    }

    fun skillThreeClick(view: View) {
        if (skill3) {
            skill3 = false
            skillThree?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
            skillThreeText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.teal_900
                )
            )
        } else {
            skill3 = true
            skillThree?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.active_feild)
            skillThreeText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.purple_200
                )
            )
        }

    }

    fun skillFourClick(view: View) {
        if (skill4) {
            skill4 = false
            skillFour?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
            skillFourText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.teal_900
                )
            )
        } else {
            skill4 = true
            skillFour?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.active_feild)
            skillFourText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.purple_200
                )
            )
        }

    }

    fun skillFiveClick(view: View) {
        if (skill5) {
            skill5 = false
            skillFive?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
            skillFiveText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.teal_900
                )
            )
        } else {
            skill5 = true
            skillFive?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.active_feild)
            skillFiveText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.purple_200
                )
            )
        }

    }

    fun skillSixClick(view: View) {
        if (skill6) {
            skill6 = false
            skillSix?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.disactive_feild)
            skillSixText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.teal_900
                )
            )
        } else {
            skill6 = true
            skillSix?.background =
                AppCompatResources.getDrawable(applicationContext, R.drawable.active_feild)
            skillSixText?.setTextColor(
                AppCompatResources.getColorStateList(
                    applicationContext,
                    R.color.purple_200
                )
            )
        }

    }

    fun onSendNextClick(view: View) {
        startLoading()
        if (skill1 || skill2 || skill3 || skill4 || skill5 || skill6) {
            val updates = HashMap<String, Any>()
            if (skill1)
                updates[skillOneText?.text.toString()] = true
            if (skill2)
                updates[skillTwoText?.text.toString()] = true
            if (skill3)
                updates[skillThreeText?.text.toString()] = true
            if (skill4)
                updates[skillFourText?.text.toString()] = true
            if (skill5)
                updates[skillFiveText?.text.toString()] = true
            if (skill6)
                updates[skillSixText?.text.toString()] = true

            userdata?.databaseRef?.child("skill")?.updateChildren(updates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        goOnNextPage()
                        finish()
                        stopLoading()
                    } else {
                        // Verification failed
                        Toast.makeText(
                            applicationContext,
                            "Your selection is not updated please try again",
                            Toast.LENGTH_LONG
                        ).show()
                        stopLoading()

                    }
                }

        } else {
            stopLoading()
            Toast.makeText(
                applicationContext,
                "Please Choose Your Skill",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}