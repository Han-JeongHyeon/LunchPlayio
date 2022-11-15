package com.example.flow

import android.content.Context
import android.content.Intent
import android.icu.text.UnicodeSet
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.Data.CheckList
import com.example.flow.Data.Data
import com.example.flow.Data.LoginData
import com.example.flow.Module.RetrofitObject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AppViewModel(private val retrofit: RetrofitObject) : ViewModel() {

//    private var _lunchList = MutableStateFlow(arrayListOf(CheckList("",false)))
//    var lunchList = _lunchList.asStateFlow()

    fun login(loginInfo: LoginData, context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val handler = Handler(Looper.getMainLooper())

        viewModelScope.launch(Dispatchers.IO) {
            var errorText = ""
//            try {
//                val id = retrofit.getRetrofitService().login(loginInfo).map { it.id }.toString()
//                intent.putExtra("id", id)
                intent.putExtra("user_id", loginInfo.id)
                context.startActivity(intent)
//            } catch (e: HttpException) {
////                Log.e("ExceptionError", "${e.code()}")
//                when (e.code()) {
//                    500 -> errorText = "아이디, 비밀번호가 일치하지 않습니다."
//                    521 -> errorText = "서버 오류."
//                }
//                handler.postDelayed({
//                    Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
//                }, 0)
//            }
        }

    }

    fun getLunchChecked(id: String?, userId: String?, context: Context){
        val checked = CheckList(
            Data(
                "$id",
                "$userId",
                "2022-11-15",
                false
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                RetrofitObject(context).getRetrofitService().lunchList(checked)
            } catch (e: Exception) {
                Log.d("TAG", "$e")
            }
        }
    }

    fun getWeekDate() = viewModelScope.launch(Dispatchers.IO) {
        val dayData: ArrayList<CheckList> = arrayListOf()

        val calendar = Calendar.getInstance()

        for (i in 0..4) {
            calendar.add(Calendar.DAY_OF_MONTH, (if (i == 0) 6 else (i + 2) - calendar.get(Calendar.DAY_OF_WEEK)))
            val weekDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

//            Log.d("TAG", "$weekDate")
//            dayData.add(CheckList(weekDate, false))
        }

//        _lunchList.emit(dayData)
    }

}