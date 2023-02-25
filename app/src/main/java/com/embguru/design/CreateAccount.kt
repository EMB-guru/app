package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.embguru.design.storage.userData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccount : AppCompatActivity() {
    private var name: EditText? =null
    private var email: EditText? =null
    private var mobileNumber: EditText? =null
    private var userdata : userData? = userData.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        define()
    }

    private fun define(){
        name=findViewById(R.id.name)
        email=findViewById(R.id.email)
        mobileNumber=findViewById(R.id.mobileNumber)
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
        } else if(email?.text.toString().isEmpty())
        {
            Toast.makeText(
                applicationContext,
                "Please Enter email Id",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        else if(!isValidEmail(email?.text.toString()))
        {
            Toast.makeText(
                applicationContext,
                "Please Enter Valid email Id",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        else if(mobileNumber?.text.toString().isEmpty())
        {
            Toast.makeText(
                applicationContext,
                "Please Enter Years of experience",
                Toast.LENGTH_LONG
            ).show()
            return false
        }else if(mobileNumber?.text.toString().length > 2)
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

    /** on button Click Handler */
    fun onSendNextClick(view: View) {
        if(validate())
        {
            val updates = HashMap<String, Any>()
            updates["name"] = name?.text.toString()
            updates["email"] = email?.text.toString()
            updates["experience"] = mobileNumber?.text.toString().toInt()
            userdata?.databaseRef?.updateChildren(updates)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    goOnNextPage()
                } else {
                    // Verification failed
                    Toast.makeText(
                        applicationContext,
                        "Currently not able to update Account details can ",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        }

    }

}