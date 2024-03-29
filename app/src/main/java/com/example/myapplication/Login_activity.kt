package jp.masashi.hakadori
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_google_login.*
import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_login.*


class Login_activity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient//googleログイン用

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id_2))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // [END config_signin]

        // Initialize Firebase Auth
        auth = Firebase.auth





        Login_button.setOnClickListener {
            val mail_address =
                input_mail.text.toString()

            val input_passwprd =
                input_password.text.toString()
            if(mail_address.isNullOrEmpty() || input_passwprd.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "mailaddressとpasswordを入力して下さい", Toast.LENGTH_LONG).show()
            }else{
                signIn(mail_address,input_passwprd)
            }

        }

        /*

        button2.setOnClickListener {
            val intent = Intent(this@Login_activity,home3::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        sign_in_button.setOnClickListener {
//            val intent = Intent(this@Login_activity,google_login::class.java)
//            startActivity(intent)
//            overridePendingTransition(0, 0)
            google_signIn()
        }

        login_account.setOnClickListener {
            val intent = Intent(this@Login_activity, Login_mail::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        logout.setOnClickListener {
            signOut()
        }

     */
    }


    private fun set_margin(height:Int,width:Int,set: View,warukazu:Int)
    {
        val MLP = set.layoutParams as ViewGroup.MarginLayoutParams
        MLP.setMargins(0,height/warukazu,0,0)
        set.layoutParams = MLP
    }

    private fun set_margin_button(height:Int,width:Int,set: View,warukazu:Int)
    {
        val MLP = set.layoutParams as ViewGroup.MarginLayoutParams
        MLP.setMargins(width/warukazu,height/warukazu,width/warukazu,0)
        set.layoutParams = MLP
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Login_activity.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "ログイン成功",
                        Toast.LENGTH_SHORT).show()
                    //user_data_store(true,false)
                    updateUI(user)
                    goto_home()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Login_activity.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "パスワードまたは、メールアドレスが違います",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null,result)
                }
            }
        // [END sign_in_with_email]
    }


    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }


    // [START signin]
    private fun google_signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Login_activity.RC_SIGN_IN)
    }
    // [END signin]

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Login_activity.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(Login_activity.GOOGLE_TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(Login_activity.GOOGLE_TAG, "Google sign in failed", e)
                textView7.text = "Google sign in failed"
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Login_activity.GOOGLE_TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Login_activity.GOOGLE_TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    // [END auth_with_google]
    private fun goto_home(){
        intent = Intent(this@Login_activity, home3::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val GOOGLE_TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        // [END auth_sign_out]
    }




}