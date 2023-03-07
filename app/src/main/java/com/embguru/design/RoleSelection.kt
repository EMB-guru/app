package com.embguru.design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import com.embguru.design.storage.category
import com.embguru.design.storage.userData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RoleSelection : AppCompatActivity() {
    private var DesignerField: LinearLayout? =null
    private var ManufacturerField: LinearLayout? =null
    private var DesignerText: TextView? =null
    private var ManufacturerText: TextView? =null
    private var useIcon: ImageView? =null
    private var ManuFactureIcon: ImageView? =null
    private var Role =1
    private var userdata : userData? =userData.getInstance()
    private var sendBtn: LinearLayout? = null
    private var Progress: ProgressBar? = null
    private var buttonText: TextView? = null

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
        sendBtn = findViewById(R.id.sendBtn)
        Progress = findViewById(R.id.Progress)
        buttonText = findViewById(R.id.buttonText)
        Progress?.visibility = View.GONE

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


    private fun goOnNextPage(){
        val changePage = Intent(this, CreateAccount::class.java)
        startActivity(changePage)
    }

    private fun setRole(role:Int){
        startLoading()
        val updates = HashMap<String, Any>()
        updates["role"] = role
        userdata?.databaseRef?.updateChildren(updates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                goOnNextPage()
                stopLoading()
                finish()
            } else {
                // Verification failed
                Toast.makeText(
                    applicationContext,
                    "Your Data is not updated please restart app and try again",
                    Toast.LENGTH_LONG
                ).show()
                stopLoading()
            }
        }
    }


    /** on button Click Handler */
    fun onDesignerClick(view: View) {
        DesignerField?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_feild)
        useIcon?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_user_icon)
        DesignerText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.purple_200))

        ManufacturerField?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.disactive_feild)
        ManuFactureIcon?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.deactive_factory)
        ManufacturerText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.teal_900))
        Role=1

    }

    fun onManufacturerClick(view: View) {
        ManufacturerField?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_feild)
        ManuFactureIcon?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.active_factory)
        ManufacturerText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.purple_200))

        DesignerField?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.disactive_feild)
        useIcon?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.inactive_user_icon)
        DesignerText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.teal_900))
        Role=2
    }

    fun onSendNextClick(view: View) {
        setRole(Role)
    }

}