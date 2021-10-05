package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityPage11Binding
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_page11.*
import kotlinx.android.synthetic.main.activity_page11.listview_page11
import java.time.LocalDate

class page11 : AppCompatActivity() {
    lateinit var realm:Realm
    lateinit var realm2:Realm
    lateinit var result_page11:RealmResults<ZikanwariDB>
    lateinit var result_task_page11:RealmResults<TaskDB>
    val spinnerItem = arrayOf("green","red","yellow","blue","skybulue")
    val color: Array<Int> = arrayOf(R.drawable.green_line, R.drawable.red_line, R.drawable.yellow_line, R.drawable.darkbulue_line, R.drawable.skybulue_line)
    var color_s:String = ""
    var icon_i:Int = 0
    var position:Int = 0
    var clickcnt:Int = 0
    var clickcnt2:Int = 0

    private val myViewModel: MyViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MyViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityPage11Binding>(this, R.layout.activity_page11)
        binding.model = myViewModel
        binding.lifecycleOwner = this

        val zikanwari:Array<String> = arrayOf("月曜1限","月曜2限","月曜3限","月曜4限","月曜5限","月曜6限",
                                                "火曜1限","火曜2限","火曜3限","火曜4限","火曜5限","火曜6限",
                                                    "水曜1限","水曜2限","水曜3限","水曜4限","水曜5限","水曜6限",
                                                        "木曜1限","木曜2限","木曜3限","木曜4限","木曜5限","木曜6限",
                                                            "金曜1限","金曜2限","金曜3限","金曜4限","金曜5限","金曜6限")


        position = intent.getStringExtra("position")!!.toInt()

        val onlyDate: LocalDate = LocalDate.now()
        val s:String = onlyDate.toString()
        val day = s.split("-")

        val m:String  = day[1].toInt().toString()
        val d:String  = day[2].toInt().toString()



        zikan_text.text = zikanwari[position]



        val spadapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item,spinnerItem)

        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPage11.adapter = spadapter

        //最初の色指定
        //spinner_page11.setSelection(icon_i)

        binding.spinnerPage11.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                color_s = parent.getItemAtPosition(position).toString()
                icon_i = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        //右下のプラスボタンその１
        event_page11.setOnClickListener {
            val intent = Intent(this@page11, page7_re::class.java)
            intent.putExtra("subject",edit_subject.text.toString())
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("name","追加")
            clickcnt++
            delete_button()
            startActivity(intent)
        }

        //右下のプラスボタンその２
        task_page11.setOnClickListener {
            val intent = Intent(this@page11, page9::class.java)
            intent.putExtra("Dayofmonth",d)
            intent.putExtra("month",m)
            intent.putExtra("year",day[0])
            intent.putExtra("name","追加")
            clickcnt++
            delete_button()
            startActivity(intent)
        }

        floatingActionButton_page11.setOnClickListener {
            if(clickcnt%2 == 0) {
                create_button()
            }else{
                delete_button()
            }
            clickcnt++
        }

        yes_page11.setOnClickListener {
            //TODO　なんかうまくいってない
            yes_page11.isClickable = false
            no_page11.isClickable = false
            delete_re.setBackgroundResource(R.color.clear)
            yes_page11.setBackgroundResource(R.color.clear)
            no_page11.setBackgroundResource(R.color.clear)
            delete_re.text = ""
            yes_page11.text = ""
            no_page11.text = ""
        }

        no_page11.setOnClickListener {
            yes_page11.isClickable = false
            no_page11.isClickable = false
            delete_re.setBackgroundResource(R.color.clear)
            yes_page11.setBackgroundResource(R.color.clear)
            no_page11.setBackgroundResource(R.color.clear)
            delete_re.text = ""
            yes_page11.text = ""
            no_page11.text = ""
        }


//                //削除ボタンが押されてた時の動作
//        button_delete_page11.setOnClickListener{
//            if(result_page11.isEmpty()) {
//                finish()
//            }else{
//                deleteRealm(position)
//            }
//        }

        button_delete_page11.setOnClickListener {
            if(edit_subject.text.isNullOrEmpty()){
                val context = getApplicationContext();
                Toast.makeText(context , "授業が登録されていません", Toast.LENGTH_LONG).show();
            }else if(clickcnt2 == 0)
            {
                yes_page11.isClickable = true
                no_page11.isClickable = true
                delete_re.text = "本当に削除しますか"
                yes_page11.text = "はい"
                no_page11.text = "いいえ"
                delete_re.setBackgroundResource(R.drawable.fillline_2)
                yes_page11.setBackgroundResource(R.drawable.fillline_2)
                no_page11.setBackgroundResource(R.drawable.fillline_2)
            }
        }


        listview_page11.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@page11,page6::class.java)
            intent.putExtra("position",position)
            startActivity(intent)
        }

        return_page11.setOnClickListener {
            finish()
        }


        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        button_delete_page11.layoutParams.height = height/20
        button_add_page11.layoutParams.height = height/20


    }

    private fun delete_button()
    {
        task_page11.isClickable = false
        event_page11.isClickable = false
        task_page11.text = ""
        event_page11.text = ""
        task_page11.setBackgroundResource(R.color.clear)
        event_page11.setBackgroundResource(R.color.clear)
    }

    private fun create_button()
    {
        task_page11.isClickable = true
        event_page11.isClickable = true
        task_page11.text = "予定を追加"
        event_page11.text = "課題を追加"
        task_page11.setBackgroundResource(R.drawable.fillline)
        event_page11.setBackgroundResource(R.drawable.fillline)
    }

    //Realmのデータを新しくする
    private fun add_new_subject(p:Int) {
        realm.beginTransaction()  //開始処理
        val zikanwariDB = realm.createObject(ZikanwariDB::class.java)
        zikanwariDB.zikanwari_title = edit_subject.text.toString()
        zikanwariDB.zikanwari_color_i = icon_i
        zikanwariDB.kyoka_date = p
        zikanwariDB.kyoka_zigen = position
        //2 zikanwariDB.zikanwari_color = color[position]
        realm.commitTransaction() //終了処理
        finish()
    }

    //Realmのデータを更新する
    private fun renewal_new_subject(p:Int){
        realm.beginTransaction()  //開始処理
        val result_renewal = realm.where(ZikanwariDB::class.java).equalTo("kyoka_date",position).findAll()
        result_renewal[0]!!.zikanwari_color_i = icon_i
        result_renewal[0]!!.zikanwari_title = edit_subject.text.toString()
        result_renewal[0]!!.kyoka_date = p
        result_renewal[0]!!.kyoka_zigen = position
        realm.commitTransaction() //終了処理
        finish()
    }


    override fun onPause() {
        super.onPause()
        realm.close()
    }

    override fun onResume() {
        super.onResume()
        //Realm作成
        realm = Realm.getDefaultInstance()
        realm2 = Realm.getDefaultInstance()

        //課題のデータを入れる
        var Task:TaskAdapter

        //Realmよりデータを抽出
        //教科
        result_page11 = realm.where(ZikanwariDB::class.java).equalTo("kyoka_date",position).findAll()


        //時間割がその時間に登録されているかを判定
        if(result_page11.isEmpty()) {
        }else {

            button_add_page11.text = "編集完了"
            val l = result_page11[0]
            edit_subject.setText(l!!.zikanwari_title)
            spinner_page11.setSelection(l.zikanwari_color_i)

            //選択した教科の課題の抽出とアダプタの設定
            result_task_page11 = realm2.where(TaskDB::class.java).equalTo("subject",l!!.zikanwari_title).findAll()
            Task = TaskAdapter(this,result_task_page11)
            listview_page11.adapter = Task
        }



        //追加ボタンが押された時の動作
        button_add_page11.setOnClickListener {
            //何も入力されていない場合は何も出さない
            if (edit_subject.text.isNullOrEmpty()){
                val context = getApplicationContext();
                Toast.makeText(context , "教科名を入力してください", Toast.LENGTH_LONG).show();
            }else {
                if(result_page11.isEmpty()) {
                    add_new_subject(position)
                    val context = getApplicationContext();
                    Toast.makeText(context , "教科が登録されました", Toast.LENGTH_LONG).show();
                    finish()
                }else{
                    renewal_new_subject(position)
                    val context = getApplicationContext();
                    Toast.makeText(context , "教科名が更新されました", Toast.LENGTH_LONG).show();
                    finish()
                }

            }
        }
        yes_page11.setOnClickListener {
            if(result_page11.isEmpty()) {
                finish()
            }else{
                deleteRealm(position)
            }
        }
        }
    //Realmからデータを削除する
    private fun deleteRealm(id: Int) {
        // プライマリーキーをもとに該当のデータを取得
        val target = realm.where(ZikanwariDB::class.java)
            .equalTo("kyoka_zigen",id)
            .findAll()

        // トランザクションして削除
        realm.executeTransaction {
            target.deleteFromRealm(0)
        }
        onPause()
        finish()
    }
}