package com.example.flow.Module

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private val fileName = "prefs"
    private val myCookie = "cookie"
    private val myLoginId = "myLoginId"
    private val myLoginPw = "myLoginPw"
    private val prefs: SharedPreferences = context.getSharedPreferences(fileName, 0)

    var token: String?
        get() = prefs.getString(myCookie, "")
        set(value) = prefs.edit().putString(myCookie, value).apply()

    var loginId: String?
        get() = prefs.getString(myLoginId, "")
        set(value) = prefs.edit().putString(myLoginId, value).apply()

    var loginPw: String?
        get() = prefs.getString(myLoginPw, "")
        set(value) = prefs.edit().putString(myLoginPw, value).apply()
}