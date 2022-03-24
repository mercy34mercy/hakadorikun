package jp.masashi.hakadori

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_home3.*
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

        textView_url.underline()
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

        var a:String = result[list_positin]!!.start_minute.toString()
        if(result[list_positin]!!.start_minute<10){
            a = "0"+ result[list_positin]!!.start_minute.toString()
        }

        var b:String = result[list_positin]!!.end_minute.toString()
        if(result[list_positin]!!.end_minute<10){
            b = "0"+ result[list_positin]!!.end_minute.toString()
        }

        textView_title.text = result[list_positin]!!.title.toString()
        textview_place.text = "場所 : " + result[list_positin]!!.place.toString()
        textView_day.text   = result[list_positin]!!.startday.toString() + "   "+ result[list_positin]!!.start_hour.toString() + ":" + a
        textview_end.text   = result[list_positin]!!.endday + "   " + result[list_positin]!!.end_hour.toString() + ":" + b
        textView_memo.text  = result[list_positin]!!.memo.toString()
        textView_url.text = result[list_positin]!!.url.toString()

        if(result[list_positin]!!.url.isNullOrEmpty()){

        }else{
            textView_url.setOnClickListener {
                intent = Intent(this@page8, webview::class.java)
                intent.putExtra("url", result[list_positin]!!.url)
                startActivity(intent)
            }
        }


    }

    fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }


}
