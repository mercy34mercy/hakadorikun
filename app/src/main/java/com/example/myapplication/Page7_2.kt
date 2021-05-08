package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page5_re.*

class Page7_2 : AppCompatActivity() {
    lateinit var realm_z: Realm
    var length: Int = 0
    lateinit var result_z : RealmResults<ZikanwariDB>
    lateinit var event_l:CustomAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page7_2)

    }

    override fun onResume() {
        super.onResume()
        realm_z = Realm.getDefaultInstance()

        //抽出
        result_z = realm_z.where(ZikanwariDB::class.java).findAll().sort("zikanwari_color")

        length = result_z.size

        //val adapter = EventA<String>(this,android.R.layout.simple_list_item_1,event_list)
        event_l = CustomAdapter2(this,result_z)
        List_Event.adapter = event_l
    }

    override fun onPause() {
        super.onPause()
    }
}