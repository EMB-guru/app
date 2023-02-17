package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class CreateAccount : AppCompatActivity() {
    private var name: EditText? =null
    private var email: EditText? =null
    private var mobileNumber: EditText? =null

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

    private fun goOnNextPage(){
        val changePage = Intent(this, IntrestPage::class.java)
        startActivity(changePage)
    }

    /** on button Click Handler */
    fun onSendNextClick(view: View) {
        goOnNextPage()
    }

}