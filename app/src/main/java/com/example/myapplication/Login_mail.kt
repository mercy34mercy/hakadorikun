package jp.masashi.hakadori

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_login_mail.*

class Login_mail : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var realm: Realm
    lateinit var result : RealmResults<UserDB>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_mail)
        auth = Firebase.auth


        create_account_button.setOnClickListener {
            val mail = email_input.text.toString()
            val password = password_input.text.toString()
            val name = name_input.text.toString()

            if(name.isNotEmpty()){
                Toast.makeText(applicationContext, "名前を入力して下さい", Toast.LENGTH_LONG).show()
            }else if(mail.isNullOrEmpty() || password.isNullOrEmpty()){
                Toast.makeText(applicationContext, "mailaddressとpasswordを入力して下さい", Toast.LENGTH_LONG).show()
            }else {
                createAccount(mail,password)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        result = realm.where(UserDB::class.java).findAll()
        val length = result.size
    }


    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,"登録完了",Toast.LENGTH_LONG).show()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Login_mail.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user,result)
                    goto_home()
                } else {
                    Toast.makeText(applicationContext,"登録失敗",Toast.LENGTH_LONG).show()
                    // If sign in fails, display a message to the user.
                    Log.w(Login_mail.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }



    private fun goto_home(){
        val intent = Intent(this@Login_mail,home3::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun updateUI(user: FirebaseUser?,user_p:RealmResults<UserDB>) {
        //TODO Realmにいろいろ保存する
        realm.beginTransaction()
        if(user_p.size != 0){
            user_p[0]?.nickname ?:name_input.text.toString()
        }else{
            val userDB = realm.createObject(UserDB::class.java)
            userDB.nickname = name_input.text.toString()
        }
        realm.commitTransaction() //終了処理
    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val GOOGLE_TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}