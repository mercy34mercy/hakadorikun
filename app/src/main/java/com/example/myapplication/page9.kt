package com.example.myapplication

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityPage9Binding
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page10.*
import kotlinx.android.synthetic.main.activity_page6.*
import kotlinx.android.synthetic.main.activity_page8.*
import kotlinx.android.synthetic.main.activity_page9.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class page9 : AppCompatActivity() {

    lateinit var realm_p: Realm
    lateinit var color_s:String
    var color_i:Int = 0
    lateinit var start_time:String
    lateinit var end_time:String
    lateinit var date_day:String
    var button_text:String = "設定完了"
    lateinit var result_page9 : RealmResults<EveDB>
    var position:Int = 0
    var resouse:Int = 0
    var sthour:Int = 0
    var stminute:Int = 0
    var endhour:Int = 0
    var endminute:Int = 0
    var task_Year:Int = 0
    var task_Month:Int = 0
    var task_Day:Int = 0

    var task_Year_now:Int = 0
    var task_Month_now:Int = 0
    var task_Day_now:Int = 0

    var syuzitu:Int = 0

    lateinit var  year:String
    lateinit var month:String
    lateinit var dayOfmonth:String

    val spinnerItem = arrayOf("red","green","blue","yellow","skybulue")

    private val scope = CoroutineScope(Dispatchers.IO)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
                DataBindingUtil.setContentView<ActivityPage9Binding>(this, R.layout.activity_page9)


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

        val onlyDate: LocalDate = LocalDate.now()
        val s:String = onlyDate.toString()
        val day_2 = s.split("-")

        task_Year_now = day_2[0].toInt()
        task_Month_now  = day_2[1].toInt()-1
        task_Day_now  = day_2[2].toInt()

        button_text = intent.getStringExtra("name").toString()
        add_button_page9.text = button_text
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

        chip_page9.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked == true){
                syuzitu = 1
                starthour_event.setBackgroundResource(R.color.gray)
                startminute_event.setBackgroundResource(R.color.gray)
                endhour_event.setBackgroundResource(R.color.gray)
                endminute_event.setBackgroundResource(R.color.gray)



            }else{
                syuzitu = 0
                starthour_event.setBackgroundResource(R.color.clear)
                startminute_event.setBackgroundResource(R.color.clear)
                endhour_event.setBackgroundResource(R.color.clear)
                endminute_event.setBackgroundResource(R.color.clear)


            }
        }

        backbutton_pasge9.setOnClickListener {
            finish()
        }



    }



    private fun addnewWord(){


        realm_p.beginTransaction()  //開始処理
        val eventDB = realm_p.createObject(EveDB::class.java)

        val color_2: Array<String> = arrayOf("green", "red", "yellow", "bulue", "skybulue")
        val color: Array<Int> = arrayOf(R.drawable.green_line, R.drawable.red_line, R.drawable.yellow_line, R.drawable.darkbulue_line, R.drawable.skybulue_line)

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
        eventDB.color_S = color_s
        eventDB.event_condition = 0
        eventDB.alltime = syuzitu


        realm_p.commitTransaction() //終了処理
        finish()
    }


    override fun onResume() {
        super.onResume()
        realm_p = Realm.getDefaultInstance()
        //追加ボタンを押したときの処理
        add_button_page9.setOnClickListener {
            val startday:LocalDateTime = generate(startdate_edit_page9.text.toString(),sthour,stminute)
            val endday:LocalDateTime =   generate(enddate_edit_page9.text.toString(),endhour,endminute)


            if (title_edit_page9.text.isNullOrEmpty()) {
                val context = getApplicationContext();
                Toast.makeText(context, "タイトルを入力してください", Toast.LENGTH_LONG).show();
            } else if (startday.isAfter(endday)) {
                val context = getApplicationContext();
                Toast.makeText(context, "終了時刻が不正です", Toast.LENGTH_LONG).show();

            } else {
                addnewWord()
                val context = getApplicationContext();
                Toast.makeText(context, "イベントが登録されました", Toast.LENGTH_LONG).show();
            }
        }


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
                task_Year_now,
                task_Month_now,
                task_Day_now)

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