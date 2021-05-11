package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityHome2Binding
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.activity_page5.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File


class home2 : AppCompatActivity() {
    //どこでも使える変数
    lateinit var realm: Realm

    private val retrofit = Retrofit.Builder().apply {
        baseUrl("https://hakadorikun.herokuapp.com/")
    }.build()

    //サービスクラスの実装オブジェクト取得
    private val service = retrofit.create(MyService::class.java)

    val get = service.getRawResponseForPosts()

    // 通信全体で利用するMediaType
    private val mediaType: MediaType = MediaType.parse("application/json; charset=utf-8")!!

    private val myViewModel: MyViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MyViewModel::class.java)
    }

    private val scope = CoroutineScope(Dispatchers.IO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//データの初期化に使う
        deleteRealm(this)


        val binding =
                DataBindingUtil.setContentView<ActivityHome2Binding>(this, R.layout.activity_home2)

        binding.model = myViewModel
        binding.lifecycleOwner = this



        setting_button.setOnClickListener {
//            intent = Intent(this@home2,Page3_re::class.java)
//            startActivity(intent)

            val i = myViewModel.result.value!!.toString()
            home_task.text = i
        }

        button_calender.setOnClickListener {
            intent = Intent(this@home2, page2::class.java)
            startActivity(intent)
        }

        button_book.setOnClickListener {
            intent = Intent(this@home2, Page3_re::class.java)
            startActivity(intent)
        }

        home_button.setOnClickListener {

        }


    }

    override fun onResume() {
        super.onResume()
        //Realmインスタンスの取得
        realm = Realm.getDefaultInstance()
    }

    override fun onPause() {
        super.onPause()
        //Realmインスタンスの後片付け
        realm.close()
    }

    private fun addnewWord() {
        realm.beginTransaction()  //開始処理
        val eventDB = realm.createObject(EveDB::class.java)
        eventDB.uid = "00000000"
        eventDB.title = "ハッカソン"
        realm.commitTransaction() //終了処理
    }

    fun deleteRealm(context: Context) {
        val dir = context.filesDir
        dir.list().forEach {
            if (it.contains("realm")) {
                File(dir, it).deleteRecursively()
            }
        }
    }
}






