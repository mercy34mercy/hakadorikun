package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page8.*

class page8 : AppCompatActivity() {
    lateinit var realm: Realm
    lateinit var result : RealmResults<EveDB>
    var list_positin:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page8)

        val list_positoin_S = intent.getIntExtra("list_position",0)
        list_positin = list_positoin_S

        button_page8_to_page9.setOnClickListener {
            intent = Intent(this@page8,page10::class.java)
            intent.putExtra("position",list_positoin_S)
            intent.putExtra("name","編集完了")
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        realm.close()
    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        //抽出
        result = realm.where(EveDB::class.java).findAll().sort("uid")
        textView_title.text = result[list_positin]!!.title.toString()
        textview_place.text = result[list_positin]!!.place.toString()
        textView_day.text   = result[list_positin]!!.startday.toString() + result[list_positin]!!.start_hour.toString() + ":" + result[list_positin]!!.start_minute.toString() + "~" + result[list_positin]!!.end_hour.toString() + ":" + result[list_positin]!!.end_minute.toString()
        textView_memo.text  = result[list_positin]!!.memo.toString()
        button_url.text     = result[list_positin]!!.url.toString()
    }


}
