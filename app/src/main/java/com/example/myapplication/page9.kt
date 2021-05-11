package com.example.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityPage9Binding
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page10.*
import kotlinx.android.synthetic.main.activity_page6.*
import kotlinx.android.synthetic.main.activity_page8.*
import kotlinx.android.synthetic.main.activity_page9.*
import kotlinx.android.synthetic.main.activity_page9.add_e_button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import retrofit2.Retrofit
import java.util.*

class page9 : AppCompatActivity() {

    lateinit var realm_p: Realm
    lateinit var color_s:String
    var color_i:Int = 0
    lateinit var start_time:String
    lateinit var end_time:String
    lateinit var date_day:String
    lateinit var button_text:String
    lateinit var result_page9 : RealmResults<EveDB>
    var position:Int = 0
    var resouse:Int = 0
    var sthour:Int = 0
    var stminute:Int = 0
    var endhour:Int = 0
    var endminute:Int = 0

    lateinit var  year:String
    lateinit var month:String
    lateinit var dayOfmonth:String

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

    val spinnerItem = arrayOf("red","green","bulue","yellow","skybulue")

    private val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
                DataBindingUtil.setContentView<ActivityPage9Binding>(this, R.layout.activity_page9)

        binding.model = myViewModel
        binding.lifecycleOwner = this




        val spadapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item,spinnerItem)
        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spadapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                color_s = parent.getItemAtPosition(position).toString()
                color_i = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        //intentを受け取る
        dayOfmonth = intent.getStringExtra("Dayofmonth").toString()
        month      = intent.getStringExtra("month").toString()
        year       = intent.getStringExtra("year").toString()
        button_text = intent.getStringExtra("name").toString()
        add_e_button.text = button_text
        val day = "$year/$month/$dayOfmonth"
        startdate_edit_page9.text = day
        enddate_edit_page9.text = day

        //文字数カウントを表示するための

        /*
        memo_edit_page9.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //処理
                var text: String = memo_edit_page9.text.toString()
                val textLength = text.length
                text_counter.text = textLength.toString() + "文字"

                val title: String = edit_title.text.toString()
                val place: String = edit_place.text.toString()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //処理
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //処理
            }
        })

         */

        starthour_event.maxValue = 23
        starthour_event.minValue = 0
        starthour_event.value = 0

        startminute_event.maxValue = 59
        startminute_event.minValue = 0
        startminute_event.value = 0

        endhour_event.maxValue = 23
        endhour_event.minValue = 0
        endhour_event.value = 0

        endminute_event.maxValue = 59
        endminute_event.minValue = 0
        endminute_event.value = 0

        starthour_event.setOnValueChangedListener { picker, oldVal, newVal ->
            sthour = newVal
        }

        startminute_event.setOnValueChangedListener { picker, oldVal, newVal ->
            stminute = newVal
        }

        endhour_event.setOnValueChangedListener { picker, oldVal, newVal ->
            endhour = newVal
        }

        endminute_event.setOnValueChangedListener { picker, oldVal, newVal ->
            endminute = newVal
        }




        //開始日を入力する
        startdate_edit_page9.setOnClickListener {
            showDatePicker(startdate_edit_page9)
        }

        //終了日を入力する
        enddate_edit_page9.setOnClickListener {
            showDatePicker(enddate_edit_page9)
        }


        //追加ボタンを押したときの処理
        add_e_button.setOnClickListener {
            addnewWord()
        }
    }



    private fun addnewWord(){



        realm_p.beginTransaction()  //開始処理
        val eventDB = realm_p.createObject(EveDB::class.java)

        val color_2: Array<String> = arrayOf("green", "red", "yellow", "bulue", "skybulue")
        val color: Array<Int> = arrayOf(R.drawable.green_line, R.drawable.red_line, R.drawable.yellow_line, R.drawable.darkbulue_line, R.drawable.white, R.drawable.skybulue_line)

        for (i in 0..4) {
            if (color_s.equals(color_2[i])) {
                resouse = color[i]
            }
        }

        eventDB.uid = "00000000"
        eventDB.title = title_edit_page9.text.toString()
        eventDB.place = place_edit_page9.text.toString()
        eventDB.startday = startdate_edit_page9.text.toString()
        eventDB.endday   = enddate_edit_page9.text.toString()
        eventDB.start_hour = sthour
        eventDB.start_minute = stminute
        eventDB.end_hour = endhour
        eventDB.end_minute = endminute
        eventDB.memo = memo_edit_page9.text.toString()
        eventDB.url = url_edit_page9.text.toString()
        eventDB.iconstyle = resouse
        eventDB.iconInt = color_i
        eventDB.event_condition = 0

        realm_p.commitTransaction() //終了処理
    }


    override fun onResume() {
        super.onResume()
        realm_p = Realm.getDefaultInstance()
    }

    override fun onPause() {
        super.onPause()
        //Realmインスタンスの後片付け
        realm_p.close()
    }

    fun showDatePicker(text_show:TextView){
        val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener() {view, year, month, dayOfMonth->
                    text_show.setText("${year}/${month + 1}/${dayOfMonth}")
                },
                2021,
                4,
                1)
        datePickerDialog.show()
    }



//    fun showTimePicker(){
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = Date().time
//
//        val timePickerDialog = TimePickerDialog(this,
//                    TimePickerDialog.OnTimeSetListener() { view, hourOfDay, minute ->
//                        editTextstartTime.setText("${hourOfDay}:${minute}")
//                        start_time = "${hourOfDay}:${minute}"
//                    }
//                    ,
//                calendar.get(Calendar.HOUR_OF_DAY),
//                calendar.get(Calendar.MINUTE),
//                true)
//        timePickerDialog.show()
//    }








}