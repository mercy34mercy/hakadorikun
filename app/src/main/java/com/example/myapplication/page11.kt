package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityPage10Binding
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_page10.*
import kotlinx.android.synthetic.main.activity_page11.*

class page11 : AppCompatActivity() {
    lateinit var realm:Realm
    lateinit var result_page11:RealmResults<ZikanwariDB>
    val spinnerItem = arrayOf("red","green","bulue","yellow","skybulue")
    var color_s:String = ""
    var icon_i:Int = 0

    private val myViewModel: MyViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityPage11Binding>(this, R.layout.activity_page11)
        //binding.model = myViewModel
        binding.lifecycleOwner = this

        button_add_page11.setOnClickListener {
            if (edit_subject.text == null){

            }else {
                add_new_subject()
                finish()
            }
        }

        button_delete_page11.setOnClickListener{
            delete_subject()
        }

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



    }

    private fun delete_subject() {
        TODO("Not yet implemented")
    }

    private fun add_new_subject() {
        val zikanwariDB = realm.createObject(ZikanwariDB::class.java)
        zikanwariDB.zikanwari_title = edit_subject.text.toString()
        zikanwariDB.zikanwari_color_i = icon_i
        zikanwariDB.kyoka_date
        zikanwariDB.kyoka_zigen
    }


    override fun onPause() {
        super.onPause()
        realm.close()
    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
        //抽出
        result_page11 = realm.where(ZikanwariDB::class.java).findAll().sort("zikanwari_color")
    }
}