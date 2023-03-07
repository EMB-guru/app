package com.embguru.design

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.denzcoskun.imageslider.models.SlideModel
import com.embguru.design.helper.getDatediff
import com.embguru.design.model.ItemsViewModel
import com.embguru.design.model.folderViewModel
import com.embguru.design.model.requirementViewModel
import com.embguru.design.storage.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlin.random.Random


class Login : AppCompatActivity() {
    private var mobileNumber: EditText? = null
    private var sendBtn: LinearLayout? = null
    private var GoogleIcon: ImageView? = null
    private var Progress: ProgressBar? = null
    private var buttonText: TextView? = null
    private var googleSignInClient: GoogleSignInClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        define()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


    }

    private fun define() {
        mobileNumber = findViewById(R.id.mobileNumber)
        sendBtn = findViewById(R.id.sendBtn)
        Progress = findViewById(R.id.Progress)
        buttonText = findViewById(R.id.buttonText)
        GoogleIcon = findViewById(R.id.GoogleIcon)
        Progress?.visibility = View.GONE

    }

    private fun startLoading() {
        Progress?.visibility = View.VISIBLE
        GoogleIcon?.visibility = View.GONE
        sendBtn?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.disable_button_background)
        buttonText?.text = "Loading ...."
        buttonText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.teal_1000))
    }

    private fun stopLoading() {
        Progress?.visibility = View.GONE
        GoogleIcon?.visibility = View.VISIBLE
        sendBtn?.background= AppCompatResources.getDrawable(applicationContext,R.drawable.button_background)
        buttonText?.text = "Sign in with Google"
        buttonText?.setTextColor(AppCompatResources.getColorStateList(applicationContext,R.color.white))
    }

    /** on button Click Handler */
    fun onSendOtpClick(view: View) {
        startLoading()

        val signInIntent = googleSignInClient?.signInIntent
        if (signInIntent != null) {

            startActivityForResult(signInIntent, 2)
        }

//        val auth = FirebaseAuth.getInstance()
//        val phoneNumber = "+91${mobileNumber?.text}" // The user's phone number with country code
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    // Automatically sign in the user if the verification is successful
//                    auth.signInWithCredential(credential)
//
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    // Handle the error
//                    stopLoading()
//                    Toast.makeText(applicationContext,e.toString(), Toast.LENGTH_LONG).show()
//                    Log.e("applicationContext",e.toString())
//
//                }
//
//                override fun onCodeSent(
//                    verificationId: String,
//                    token: PhoneAuthProvider.ForceResendingToken
//                ) {
//                    val changePage = Intent(applicationContext, Otp::class.java)
//                        .putExtra("verificationId", verificationId)
//                        .putExtra("PhoneNumber", "+91${mobileNumber?.text}")
//
//                    startActivity(changePage)
//                }
//            })
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    fun generateRandomString(length: Int): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                stopLoading()
                Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_LONG).show()

                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val firebaseAuth =FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    if(!userData.isUserLogin(applicationContext,this))
                    {
                        val changePage = Intent(this, Feature_Screen_1::class.java)
                        startActivity(changePage)
                        finish()
                    }

                } else {
                    stopLoading()
                    Toast.makeText(applicationContext,"Something wants wrong try again",Toast.LENGTH_LONG).show()

                }
            }
    }

}