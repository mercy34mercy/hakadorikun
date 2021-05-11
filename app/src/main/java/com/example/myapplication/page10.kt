package com.example.myapplication

import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityPage10Binding
import com.example.myapplication.databinding.ActivityPage9Binding
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_memotest.view.*
import kotlinx.android.synthetic.main.activity_page10.*
import kotlinx.android.synthetic.main.activity_page10.add_e_button

import kotlinx.android.synthetic.main.activity_page8.*
import kotlinx.android.synthetic.main.activity_page9.*

class page10 : AppCompatActivity() {
    lateinit var realm:Realm
    lateinit var result_page10: RealmResults<EveDB>
    lateinit var color_s:String
    var position:Int = 0
    lateinit var name_s:String
    var icon_i:Int = 0
    val spinnerItem = arrayOf("red","green","bulue","yellow","skybulue")
    var resouse:Int = 0
    var sthour:Int = 0
    var stminute:Int = 0
    var endhour:Int = 0
    var endminute:Int = 0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
                DataBindingUtil.setContentView<ActivityPage10Binding>(this, R.layout.activity_page10)
        //binding.model = myViewModel
        binding.lifecycleOwner = this









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

        spinner_page10.setSelection(icon_i)

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

        sthour_page10.maxValue = 23
        sthour_page10.minValue = 0
        sthour_page10.value = 0

        stminute_page10.maxValue = 59
        stminute_page10.minValue = 0
        stminute_page10.value = 0

        endhour_page10.maxValue = 23
        endhour_page10.minValue = 0
        endhour_page10.value = 0

        endminte_page10.maxValue = 59
        endminte_page10.minValue = 0
        endminte_page10.value = 0






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

    }

    fun chenge_event(realm_p:RealmResults<EveDB>){

        realm.beginTransaction()

        val color_2: Array<String> = arrayOf("green", "red", "yellow", "bulue", "skybulue")
        val color: Array<Int> = arrayOf(R.drawable.green_line, R.drawable.red_line, R.drawable.yellow_line, R.drawable.darkbulue_line, R.drawable.skybulue_line)

        for (i in 0..5) {
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
        realm.commitTransaction()

        title_edit_page10.setText("")
        place_edit_page10.setText("")
        text_startday_page10.text = ""
        text_endday_page10.text   = ""
        memo_edit_page10.setText("")
        url_edit_page10.setText("")

        finish()


    }


}