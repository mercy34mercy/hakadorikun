package com.example.myapplication

import io.realm.RealmObject
import java.sql.Types.NULL

open class EveDB:RealmObject() {
    //フィールドの設定
    var uid: String = ""
    var startday: String = ""
    var endday: String= ""
    var start_hour:Int = NULL
    var start_minute:Int = NULL
    var end_hour:Int = NULL
    var end_minute:Int = NULL
    var memo:String= ""
    var place:String = ""
    var title: String = ""
    var url:String = ""
    var iconstyle: Int = 0
    var iconInt:Int = 0
    var color_S:String = ""
    var alltime:Int = 0
    var event_condition:Int = 0
}

