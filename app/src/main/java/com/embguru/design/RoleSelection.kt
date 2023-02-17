package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class RoleSelection : AppCompatActivity() {
    private var DesignerField: LinearLayout? =null
    private var ManufacturerField: LinearLayout? =null
    private var DesignerText: TextView? =null
    private var ManufacturerText: TextView? =null
    private var useIcon: ImageView? =null
    private var ManuFactureIcon: ImageView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)
        define()
    }

    private fun define(){
        DesignerField=findViewById(R.id.DesignerField)
        ManufacturerField=findViewById(R.id.ManufacturerField)
        DesignerText=findViewById(R.id.DesignerText)
        ManufacturerText=findViewById(R.id.ManufacturerText)
        useIcon=findViewById(R.id.useIcon)
        ManuFactureIcon=findViewById(R.id.ManuFactureIcon)

    }

    private fun goOnNextPage(){
        val changePage = Intent(this, CreateAccount::class.java)
        startActivity(changePage)
    }


    /** on button Click Handler */
    fun onDesignerClick(view: View) {
        DesignerField?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_feild)
        useIcon?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_user_icon)
        DesignerText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.purple_200))

        ManufacturerField?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.disactive_feild)
        ManuFactureIcon?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.deactive_factory)
        ManufacturerText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.teal_900))
    }

    fun onManufacturerClick(view: View) {
        ManufacturerField?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_feild)
        ManuFactureIcon?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_factory)
        ManufacturerText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.purple_200))

        DesignerField?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.disactive_feild)
        useIcon?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.inactive_user_icon)
        DesignerText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.teal_900))
    }

    fun onSendNextClick(view: View) {
        goOnNextPage()
    }

}