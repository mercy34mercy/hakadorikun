package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_mail.*

class Login_mail : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_mail)
        auth = Firebase.auth

        sing_in_button.setOnClickListener {
            val mail = email_input.text.toString()
            val password = password_input.text.toString()
            if(mail.isNullOrEmpty() || password.isNullOrEmpty()){
                Toast.makeText(applicationContext, "mailaddressとpasswordを入力して下さい", Toast.LENGTH_LONG).show()
            }else {
                signIn(mail,password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Login_mail.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    goto_home()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Login_mail.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun goto_home(){
        val intent = Intent(this@Login_mail,home3::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun updateUI(user: FirebaseUser?) {
        //TODO Realmにいろいろ保存する

    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val GOOGLE_TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}