package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityPage7ReBinding
import com.example.myapplication.databinding.ActivityPage9Binding
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_home3.*
import kotlinx.android.synthetic.main.activity_page7_re.*
import java.time.LocalDate
import kotlin.math.min

class page7_re : AppCompatActivity() {
    lateinit var realm_page7: Realm
    lateinit var result: RealmResults<TaskDB>
    lateinit var subjct_result: RealmResults<ZikanwariDB>
    lateinit var subjct_result2: RealmResults<ZikanwariDB>
    lateinit var end_time:String
    lateinit var title_get:String
    lateinit var name_button:String
    var position:Int = 0
    var hour:Int = 0
    var minute:Int = 0
    var task_Year:Int = 0
    var task_Month:Int = 0
    var task_Day:Int = 0
    var spinnerItem = arrayOf("その他")
    var subject_i:Int = 0
    var subject_s:String = ""
    lateinit var binding:ActivityPage7ReBinding

    var task_Year_now:Int = 0
    var task_Month_now:Int = 0
    var task_Day_now:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityPage7ReBinding>(this, R.layout.activity_page7_re)


        binding.lifecycleOwner = this






        //intentを受け取る

        val dayOfmonth:String = intent.getStringExtra("Dayofmonth").toString()
        val month:String      = intent.getStringExtra("month").toString()
        val year:String       = intent.getStringExtra("year").toString()


        val onlyDate: LocalDate = LocalDate.now()
        val s:String = onlyDate.toString()
        val day = s.split("-")

        task_Year_now = day[0].toInt()
        task_Month_now  = day[1].toInt()-1
        task_Day_now  = day[2].toInt()



        dead_day_page7.text = year+"/"+month+"/"+dayOfmonth
        name_button = intent.getStringExtra("name").toString()
        add_button.text = name_button


        //add_button.isEnabled = false


        if(name_button.equals("編集完了")) {
            position = intent.getIntExtra("position",0)
        }



//        dead_time_page7.addTextChangedListener(object: TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                //処理
//                end_time = dead_time_page7.text.toString()
//                if(add_button.text.equals("")){
//                    add_button.isEnabled = false
//                }else {
//                    add_button.isEnabled = true
//                }
//            }

//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                //処理
//
//            }
//        })

        title_edit_page7.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //処理
                title_get = dead_day_page7.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //処理
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //処理
            }
        })



        add_button.setOnClickListener {
            if(name_button.equals("編集完了")) {
                add_task(hour,minute)
            }else {
                post_task(hour, minute)
            }
        }

        dead_day_page7.setOnClickListener {
            showDatePicker(dead_day_page7)
        }

//        home_task.setOnClickListener {
//            val intent = Intent(this@page7_re,page11::class.java)
//
//            startActivity(intent)
//        }

        cancel_page7.setOnClickListener {
            finish()
        }

        this.initNumberhourPicker()
        this.initNumbertimePicker()





    }

    private fun initNumbertimePicker(){
        Number_time.minValue = 0
        Number_time.maxValue = 59

        Number_time.setOnValueChangedListener { picker, oldVal, newVal ->
            minute = newVal
        }
    }

    private fun initNumberhourPicker(){
        Number_hour.minValue = 0
        Number_hour.maxValue = 23

        Number_hour.setOnValueChangedListener { picker, oldVal, newVal ->
            hour = newVal
        }

    }

    override fun onResume() {
        super.onResume()
        realm_page7 = Realm.getDefaultInstance()
        //抽出


        result = realm_page7.where(TaskDB::class.java).findAll().sort("task_uid")
        subjct_result = realm_page7.where(ZikanwariDB::class.java).findAll()

        val result_size:Int = subjct_result.size





        for (i in 0 .. result_size-1) {
            spinnerItem += subjct_result[i]!!.zikanwari_title
        }

        //spnnerの設定
        val spadapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item,spinnerItem)
        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spadapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                subject_s = parent.getItemAtPosition(position).toString()
                subject_i = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        if(name_button.equals("編集完了")) {
            setText()
        }




    }


    override fun onPause() {
        super.onPause()
        realm_page7.close()
    }



    fun post_task(hour:Int,minute:Int){


            realm_page7.beginTransaction()  //開始処理
            val taskDB: TaskDB = realm_page7.createObject(TaskDB::class.java)

            taskDB.task_uid = "00000000"
            taskDB.task_title = title_edit_page7.text.toString()
            //taskDB.subject = Kamoku_edit_button.text.toString()
            taskDB.subject = subject_s
            taskDB.zikanwari_color_task = subject_i
            taskDB.dead_day = dead_day_page7.text.toString()
            taskDB.task_url = url_edit_page7.text.toString()
            taskDB.task_memo = memo_edit_page7.text.toString()
            taskDB.dead_hour = hour
            taskDB.dead_minute =  minute
            taskDB.task_year = task_Year
            taskDB.task_month = task_Month
            taskDB.task_day = task_Day
            taskDB.task_number = create_number(task_Year,task_Month,task_Day,hour,minute)



            realm_page7.commitTransaction() //終了処理
        finish()

    }










    fun showDatePicker(text_show: TextView){
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth->
                text_show.setText("${year}/${month + 1}/${dayOfMonth}")
                task_Year = year
                task_Month = month
                task_Day = dayOfMonth
            },
            task_Year_now,
            task_Month_now,
            task_Day_now)
        datePickerDialog.show()
    }

    fun setText(){
        val r = result[position]
        realm_page7.beginTransaction()  //開始処理
        title_edit_page7.setText(r!!.task_title)
        dead_day_page7.text = r!!.dead_day
        //Number_hour.setText(r!!.dead_time)
        url_edit_page7.setText(r!!.task_url)
        memo_edit_page7.setText(r.task_memo)
        spinner.setSelection(r!!.zikanwari_color_task)
        hour = r!!.dead_hour
        minute = r!!.dead_minute

        Number_hour.value = hour
        Number_time.value = minute

        realm_page7.commitTransaction()
    }

    fun add_task(hour:Int,minute: Int){
        val t = result[position]
        realm_page7.beginTransaction()  //開始処理
        t!!.task_title = title_edit_page7.text.toString()
        t!!.subject   =  subject_s
        t!!.zikanwari_color_task = subject_i
        t!!.dead_day = dead_day_page7.text.toString()
        t!!.dead_minute  = minute
        t!!.dead_hour    =  hour
        t!!.task_url   = url_edit_page7.text.toString()
        t!!.task_memo  = memo_edit_page7.text.toString()
        t!!.task_year = task_Year
        t!!.task_month = task_Month
        t!!.task_day = task_Day
        t!!.task_number = com.example.myapplication.create_number(task_Year,task_Month,task_Day,hour,minute)
        realm_page7.commitTransaction()
        finish()
    }
}