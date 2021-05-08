package com.example.myapplication

import io.realm.RealmObject
import java.sql.Types.NULL

open class TaskDB: RealmObject() {
    var task_uid = ""
    var task_title:String = ""
    var subject:String = ""
    var dead_minute:Int = NULL
    var dead_hour:Int = NULL
    var dead_day:String = ""
    var task_url:String = ""
    var task_memo:String = ""
    var task_condition:Int = NULL

    var zikanwari_color_task:Int = 0
    var zikanwari_color_i_task:Int = 0
    var zikanwari_title_task:String = ""
}