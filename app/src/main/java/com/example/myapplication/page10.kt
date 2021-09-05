package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.Transliterator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityPage10Binding
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page10.*
import kotlinx.android.synthetic.main.activity_page10.add_e_button
import java.time.LocalDate

class page10 : AppCompatActivity() {
    lateinit var realm:Realm
    lateinit var result_page10: RealmResults<EveDB>
    lateinit var color_s:String
    var position:Int = 0
    lateinit var name_s:String
    var icon_i:Int = 0
    val spinnerItem = arrayOf("red","green","blue","yellow","skybulue")
    var resouse:Int = 0
    var sthour:Int = 0
    var stminute:Int = 0
    var endhour:Int = 0
    var endminute:Int = 0

    var task_Year_now:Int = 0
    var task_Month_now:Int = 0
    var task_Day_now:Int = 0

    var syuzitu:Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
                DataBindingUtil.setContentView<ActivityPage10Binding>(this, R.layout.activity_page10)
        //binding.model = myViewModel
        binding.lifecycleOwner = this







        val onlyDate: LocalDate = LocalDate.now()
        val s:String = onlyDate.toString()
        val day = s.split("-")

        task_Year_now = day[0].toInt()
        task_Month_now  = day[1].toInt()-1
        task_Day_now  = day[2].toInt()


        val position_S = intent.getIntExtra("position",0)
        position = position_S
        val name = intent.getStringExtra("name")
        name_s = name.toString()




        add_e_button.setOnClickListener {
            chenge_event(result_page10)

        }

        val spadapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_item,spinnerItem)

        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPage10.adapter = spadapter



        binding.spinnerPage10.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                color_s = parent.getItemAtPosition(position).toString()
                icon_i = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        chip_page10.setOnCheckedChangeListener { buttonView, isChecked ->
            if(chip_page10.isChecked){
                syuzitu = 1
                sthour_page10.setBackgroundResource(R.color.gray)
                stminute_page10.setBackgroundResource(R.color.gray)
                endhour_page10.setBackgroundResource(R.color.gray)
                endminte_page10.setBackgroundResource(R.color.gray)
            }else{
                syuzitu = 0
                sthour_page10.setBackgroundResource(R.color.clear)
                stminute_page10.setBackgroundResource(R.color.clear)
                endhour_page10.setBackgroundResource(R.color.clear)
                endminte_page10.setBackgroundResource(R.color.clear)
            }
        }

        text_startday_page10.setOnClickListener {
            showDatePicker(text_startday_page10)
        }

        //終了日を入力する
        text_endday_page10.setOnClickListener {
            showDatePicker(text_endday_page10)
        }



        sthour_page10.maxValue = 23
        sthour_page10.minValue = 0


        stminute_page10.maxValue = 59
        stminute_page10.minValue = 0


        endhour_page10.maxValue = 23
        endhour_page10.minValue = 0


        endminte_page10.maxValue = 59
        endminte_page10.minValue = 0




        sthour_page10.setOnValueChangedListener { picker, oldVal, newVal ->
            sthour = newVal
        }

        stminute_page10.setOnValueChangedListener { picker, oldVal, newVal ->
            stminute = newVal
        }

        endhour_page10.setOnValueChangedListener { picker, oldVal, newVal ->
            endhour = newVal
        }

        endminte_page10.setOnValueChangedListener { picker, oldVal, newVal ->
            endminute = newVal
        }

//        val m = result_page10[position]
//        m!!.start_hour








    }


    override fun onPause() {
        super.onPause()
        //Realmインスタンスの後片付け
        realm.close()
    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()

        //抽出
        result_page10 = realm.where(EveDB::class.java).findAll().sort("uid")
        text_set(result_page10)
        val l = result_page10[position]
        color_s = l!!.color_S

        spinner_page10.setSelection(l!!.iconInt)

        delete_page10.setOnClickListener {
            val target = l

            realm.executeTransaction {
                intent = Intent(this@page10, home3::class.java)
                startActivity(intent)
                target.deleteFromRealm()
            }
        }
        //set_spinner_value(l!!.start_hour,l!!.start_minute,l!!.end_hour,l!!.end_minute)





    }

    private fun set_spinner_value(a:Int,b:Int,c:Int,d:Int) {
        sthour_page10.maxValue = 23
        sthour_page10.minValue = 0
        sthour_page10.value = a

        stminute_page10.maxValue = 59
        stminute_page10.minValue = 0
        stminute_page10.value = b

        endhour_page10.maxValue = 23
        endhour_page10.minValue = 0
        endhour_page10.value = c

        endminte_page10.maxValue = 59
        endminte_page10.minValue = 0
        endminte_page10.value = d
    }

    fun text_set(realm_s:RealmResults<EveDB>){

        val r = realm_s[position]
        //edit_title.setText(realm_s[position]!!.title)
        title_edit_page10.setText(r!!.title)
        place_edit_page10.setText(r.place)
        text_startday_page10.text = r.startday
        text_endday_page10.text   = r.endday
        memo_edit_page10.setText(r.memo)
        url_edit_page10.setText(r.url)
        add_e_button.text = name_s
        icon_i = r.iconInt

        sthour = r!!.start_hour
        stminute = r!!.start_minute
        endhour = r!!.end_hour
        endminute = r!!.end_minute

        if(r.alltime == 1) {
            chip_page10.isChecked = true
        }else{
            chip_page10.isChecked = false
        }

        endminte_page10.value = r!!.end_minute
        endhour_page10.value = r!!.end_hour
        stminute_page10.value  = r!!.start_minute
        sthour_page10.value = r!!.start_hour

    }

    fun chenge_event(realm_p:RealmResults<EveDB>){

        realm.beginTransaction()

        val color_2: Array<String> = arrayOf("green", "red", "yellow", "bulue", "skybulue")
        val color: Array<Int> = arrayOf(R.drawable.green_line, R.drawable.red_line, R.drawable.yellow_line, R.drawable.darkbulue_line, R.drawable.skybulue_line)

        for (i in 0..4) {
            if (color_s.equals(color_2[i])) {
                resouse = color[i]
            }
        }

        val selectDB = realm_p[position]
        selectDB!!.title = title_edit_page10.text.toString()
        selectDB!!.place = place_edit_page10.text.toString()
        selectDB!!.startday = text_startday_page10.text.toString()
        selectDB!!.endday   = text_endday_page10.text.toString()
        selectDB.start_hour = sthour
        selectDB.start_minute = stminute
        selectDB.end_hour = endhour
        selectDB.end_minute = endminute
        selectDB!!.memo       = memo_edit_page10.text.toString()
        selectDB!!.url        = url_edit_page10.text.toString()
        selectDB!!.iconstyle  = resouse
        selectDB!!.iconInt    = icon_i
        selectDB!!.color_S = color_s
        selectDB!!.alltime = syuzitu
        realm.commitTransaction()
        finish()



    }

    fun showDatePicker(text_show: TextView){
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() { view, year, month, dayOfMonth->
                text_show.setText("${year}/${month + 1}/${dayOfMonth}")
            },
            task_Year_now,
            task_Month_now,
            task_Day_now)
        datePickerDialog.show()
    }


}


