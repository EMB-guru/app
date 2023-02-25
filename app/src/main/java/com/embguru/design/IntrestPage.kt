package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.embguru.design.storage.userData

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intrest_page)
        define()
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

            userdata?.databaseRef?.child("skill")?.updateChildren(updates)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    goOnNextPage()
                } else {
                    // Verification failed
                    Toast.makeText(
                        applicationContext,
                        "Your Otp is not correct please try again",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        } else {
            Toast.makeText(
                applicationContext,
                "Please Choose Your Skill",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}