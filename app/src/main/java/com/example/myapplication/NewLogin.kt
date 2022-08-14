package jp.masashi.hakadori

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import jp.masashi.hakadori.google_login
import kotlinx.android.synthetic.main.activity_newlogin.*


class NewLogin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient//googleログイン用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newlogin)

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id_2))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // [END config_signin]


        auth = Firebase.auth


//        google_login.setOnClickListener {
//            google_signIn()
//            val intent = Intent(this@NewLogin,home3::class.java)
//            startActivity(intent)
//            overridePendingTransition(0, 0)
//        }

        emailbutton.setOnClickListener {
            intent = Intent(this@NewLogin, Login_mail::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        gotoLogin.setOnClickListener {
            intent = Intent(this@NewLogin,Login_activity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    // [START signin]
    private fun google_signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, NewLogin.RC_SIGN_IN)
    }
    // [END signin]

    companion object {
        private const val TAG = "EmailPassword"
        private const val GOOGLE_TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

}