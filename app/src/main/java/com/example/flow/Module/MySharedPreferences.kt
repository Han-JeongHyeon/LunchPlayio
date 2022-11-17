package com.example.flow.Module

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private val fileName = "prefs"
    private val myCookie = "cookie"
    private val prefs: SharedPreferences = context.getSharedPreferences(fileName, 0)

    var token: String?
        get() = prefs.getString(myCookie, "")
        set(value) = prefs.edit().putString(myCookie, value).apply()

}