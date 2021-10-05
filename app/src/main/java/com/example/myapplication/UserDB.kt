package com.example.myapplication

import io.realm.RealmObject

open class UserDB: RealmObject()
{
    var user_email_login:Boolean = false
    var user_google_login:Boolean = false
    var tuuti:Boolean = false
    var mailaddress:String = ""
    var gakubu:String = ""
    var gakka:String = ""
    var grade:String = ""
    var nickname:String = ""
}