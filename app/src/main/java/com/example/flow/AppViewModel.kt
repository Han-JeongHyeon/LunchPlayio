package com.example.flow

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.Data.CheckList
import com.example.flow.Data.Data
import com.example.flow.Data.GetTableData
import com.example.flow.Data.LoginData
import com.example.flow.Module.App
import com.example.flow.Module.RetrofitObject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import retrofit2.http.HTTP
import java.lang.Exception

class AppViewModel(retrofit: RetrofitObject) : ViewModel() {

    private var _lunchList = MutableSharedFlow<List<GetTableData>>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    var lunchList = _lunchList.asSharedFlow()

    private val retrofitService = retrofit.getRetrofitService()

    fun login(loginInfo: LoginData, context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val handler = Handler(Looper.getMainLooper())

        viewModelScope.launch(Dispatchers.IO) {
            var errorText = ""
            try {
                val id = retrofitService.login(loginInfo).map { it.id }[0]
                intent.putExtra("id", id)
                context.startActivity(intent)
            } catch (e: HttpException) {
                when (e.code()) {
                    500 -> errorText = "아이디, 비밀번호가 일치하지 않습니다."
                    502 -> errorText = "서버 오류 502"
                    521 -> errorText = "서버 오류 521"
                }
                handler.postDelayed({
                    Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
                }, 0)
            }
        }

    }

    fun getLunchChecked(item: GetTableData, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                retrofitService.lunchList(
                    CheckList(
                        Data(
                            item.id,
                            item.user_id,
                            item.d_id,
                            if (isChecked) 1 else 0
                        )
                    )
                )
            } catch (e: HttpException) {
                Log.d("Http ", "$e")
            }
            getTableDate(item.user_id)
        }
    }

    fun getTableDate(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lunch = retrofitService.getTableData()
                lunch.map { it.user_id = id }
                _lunchList.emit(lunch)
            } catch (e: HttpException) {
                Log.d("Http ", "$e")
            }
        }
    }

}