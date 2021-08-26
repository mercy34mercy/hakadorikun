package com.example.myapplication

import io.realm.RealmObject

open class UserDB: RealmObject()
{
    var user_email_login:Boolean = false
    var user_google_login:Boolean = false
}