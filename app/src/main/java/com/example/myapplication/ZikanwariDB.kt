package com.example.myapplication

import io.realm.RealmObject

open class ZikanwariDB:RealmObject() {
    var zikanwari_color:Int = 0
    var zikanwari_color_i:Int = 0
    var zikanwari_title:String = ""
    var kyoka_date:Int = 0
    var kyoka_zigen:Int = 0
}