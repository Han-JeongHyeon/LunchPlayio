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
import com.example.flow.Module.RetrofitObject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import java.lang.Exception

class AppViewModel(private val retrofit: RetrofitObject) : ViewModel() {

    private var _lunchList = MutableStateFlow(listOf(GetTableData("", "", "", "", "", "", "")))
    var lunchList = _lunchList.asStateFlow()

    fun login(loginInfo: LoginData, context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val handler = Handler(Looper.getMainLooper())

        viewModelScope.launch(Dispatchers.IO) {
            var errorText = ""
            try {
                val id = retrofit.getRetrofitService("").login(loginInfo).map { it.id }[0]
                intent.putExtra("id", id)
                intent.putExtra("userName", loginInfo.id)
                context.startActivity(intent)
            } catch (e: HttpException) {
                when (e.code()) {
                    500 -> errorText = "아이디, 비밀번호가 일치하지 않습니다."
                    521 -> errorText = "서버 오류."
                }
                handler.postDelayed({
                    Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
                }, 0)
            }
        }

    }

    fun getLunchChecked(item: GetTableData, isChecked: Boolean) {
        Log.d("TAG", "${item.id}, ${item.user_id}, ${item.d_id}, ${if(isChecked) 1 else 0}")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                retrofit.getRetrofitService("").lunchList(
                    CheckList(
                        Data(
                            item.id,
                            item.user_id,
                            item.d_id,
                            if (isChecked) 1 else 0
                        )
                    )
                )
            } catch (e: Exception) {
                Log.d("TAG", "$e")
            }
        }
    }

    fun getTableDate(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lunch = retrofit.getRetrofitService(id).getTableData()
                lunch.map { it.user_id = id }
                _lunchList.emit(lunch)
            } catch (e: HttpException) {
                Log.d("TAG", "$e")
            }
        }
    }

}