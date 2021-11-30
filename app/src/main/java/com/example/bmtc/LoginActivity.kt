package com.example.bmtc

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {


    lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        mAuth = FirebaseAuth.getInstance()




        if (mAuth.currentUser != null) {

            if(mAuth.currentUser!!.email == "conductor123@bendre.com"){

               //todo

            }else if(mAuth.currentUser!!.email == "admin123@bendre.com"){

                //todo

            } else{

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        }

        //val fbLoginBtn1 = findViewById<LoginButton>(R.id.fbLoginBtn)


      //  val animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
          //  R.anim.slide_anim);


        val display: Display = windowManager.defaultDisplay
        val width = display.getWidth()
        val animation =
            TranslateAnimation(0F, (width - 50).toFloat(), 0.0F, 0.0F) // new TranslateAnimation(xFrom,xTo, yFrom,yTo)

        animation.setDuration(1000) // animation duration

        animation.setRepeatCount(5) // animation repeat count

        animation.setRepeatMode(2) // repeat animation (left to right, right to

        // left )
        // animation.setFillAfter(true);

        // left )
        // animation.setFillAfter(true);
        imageView4.startAnimation(animation) // start animati



    }

    fun takeToSignUp(view: View){

        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)

    }

    fun loginClicked(view: View){

        val email = emailTxtLogin.text.toString()
        val password = passwordTxtLogin.text.toString()

        if(email.isEmpty() || password.isEmpty() ) {

            if (email.isEmpty()) emailTxtLogin.error = resources.getString(R.string.enter_email)
            if (password.isEmpty()) passwordTxtLogin.error = resources.getString(R.string.enter_password)


        }else{

            if (password.length < 8){
                //passwordTxtLogin.error = "message"
            }
            login(email,password)

        }

    }

    private fun showFailure( message: String?) {

        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(resources.getString(R.string.bendre_transports))
        //set message for alert dialog
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton(resources.getString(R.string.ok)){ p,q ->

        }

        builder.show()

    }

    fun forgotPasswordClicked(view: View){

        Log.d ("Login","${view.id}")
        val intent = Intent(this,
            ForgotPasswordActivity::class.java)
        startActivity(intent)

    }


    fun login(email: String, password: String){

        val progress = ProgressDialog(this)
        progress.setTitle(resources.getString(R.string.bendre_transports))
        progress.setMessage(resources.getString(R.string.logging_in))
        progress.setCancelable(false)
        progress.show()


        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {task ->

                if (task.isSuccessful) {

                    progress.dismiss()

                    Toast.makeText(this, resources.getString(R.string.login_success) , Toast.LENGTH_SHORT).show()

                    if(mAuth.currentUser!!.email == "conductor123@bendre.com")
                    {

                      /*  val intent = Intent(this,
                            ConductorMainActivity::class.java)
                        startActivity(intent)
                        finish()*/

                    }else if(mAuth.currentUser!!.email == "admin123@bendre.com"){

                       /* val intent = Intent(this, AdminActivity::class.java)
                        startActivity(intent)
                        finish()*/

                    } else{

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }



                }

            }
            .addOnFailureListener { err ->

                 progress.dismiss()
                 showFailure(err.message)

            }


    }

    fun setLocate(Lang: String) {

        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()

    }

    fun loadLocate() {

        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language!!)

    }
}
