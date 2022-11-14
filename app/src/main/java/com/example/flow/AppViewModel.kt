package com.example.flow

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.Data.CheckList
import com.example.flow.Data.LoginData
import com.example.flow.Module.RetrofitObject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AppViewModel(private val retrofit: RetrofitObject) : ViewModel() {

    private var _lunchList = MutableStateFlow(arrayListOf(CheckList("",false)))
    var lunchList = _lunchList.asStateFlow()

    fun login(loginInfo: LoginData, context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val handler = Handler(Looper.getMainLooper())

        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val id = retrofit.getRetrofitService().login(loginInfo).map { it.id }.toString()
//                intent.putExtra("id", id)
                context.startActivity(intent)
//            } catch (e: Exception) {
//                Log.e("ExceptionError", e.toString())
//                handler.postDelayed({
//                    Toast.makeText(
//                        context,
//                        "아이디, 비밀번호가 일치하지 않습니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }, 0)
//            }
        }

    }

    fun getWeekDate() = viewModelScope.launch(Dispatchers.IO) {
        val dayData: ArrayList<CheckList> = arrayListOf()

        val calendar = Calendar.getInstance()

        calendar.add(Calendar.DAY_OF_MONTH, (9-calendar.get(Calendar.DAY_OF_WEEK)))

        for (i in 0..4) {
            calendar.add(Calendar.DAY_OF_MONTH, ((i + 2) - calendar.get(Calendar.DAY_OF_WEEK)))
            val weekDate = SimpleDateFormat("yyyy-MM-dd (E)", Locale.getDefault()).format(calendar.time)

            dayData.add(CheckList(weekDate, false))
        }

        _lunchList.emit(dayData)
    }

}