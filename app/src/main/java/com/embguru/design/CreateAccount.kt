package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import com.embguru.design.storage.userData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccount : AppCompatActivity() {
    private var name: EditText? =null
    private var mobileNumber: EditText? =null
    private var experience: EditText? =null
    private var userdata : userData? = userData.getInstance()
    private var sendBtn: LinearLayout? = null
    private var Progress: ProgressBar? = null
    private var buttonText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        define()
    }

    private fun define(){
        name=findViewById(R.id.name)
        mobileNumber=findViewById(R.id.mobileNumber)
        experience=findViewById(R.id.experience)
        sendBtn = findViewById(R.id.sendBtn)
        Progress = findViewById(R.id.Progress)
        buttonText = findViewById(R.id.buttonText)
        Progress?.visibility = View.GONE
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,})+$")
        return emailRegex.matches(email)
    }
    private fun validate(): Boolean {
        if(name?.text.toString().isEmpty())
        {
            Toast.makeText(
                applicationContext,
                "Please Enter Name",
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if(mobileNumber?.text.toString().isEmpty())
        {
            Toast.makeText(
                applicationContext,
                "Please Enter Mobile number Id",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        else if(mobileNumber?.text.toString().length!=10)
        {
            Toast.makeText(
                applicationContext,
                "Please Enter Valid 10 digit mobile number",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        else if(experience?.text.toString().isEmpty())
        {
            Toast.makeText(
                applicationContext,
                "Please Enter Years of experience",
                Toast.LENGTH_LONG
            ).show()
            return false
        }else if(experience?.text.toString().length > 2)
        {
            Toast.makeText(
                applicationContext,
                "Please Enter years of experience between 0 to 99",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    private fun goOnNextPage(){
        val changePage = Intent(this, IntrestPage::class.java)
        startActivity(changePage)
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
    fun onSendNextClick(view: View) {
        startLoading()
        if(validate())
        {
            val updates = HashMap<String, Any>()
            updates["name"] = name?.text.toString()
            updates["mobileNumber"] = "+91${mobileNumber?.text.toString()}"
            updates["email"] = "${userdata?.email}"
            updates["experience"] = experience?.text.toString().toInt()
            userdata?.databaseRef?.updateChildren(updates)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    goOnNextPage()
                    stopLoading()
                    finish()
                } else {
                    // Verification failed
                    Toast.makeText(
                        applicationContext,
                        "Currently not able to update Account details can ",
                        Toast.LENGTH_LONG
                    ).show()
                    stopLoading()

                }
            }

        }else{
            stopLoading()
        }

    }

}