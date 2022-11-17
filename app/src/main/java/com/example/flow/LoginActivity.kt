package com.example.flow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flow.Data.LoginData
import com.example.flow.Module.App
import com.example.flow.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val appViewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idEd.setText(App.prefs.loginId)
        binding.passwordEd.setText(App.prefs.loginPw)

        binding.loginBtn.setOnClickListener {
            if (binding.idEd.text.isEmpty() || binding.passwordEd.text.isEmpty()) {
                return@setOnClickListener
            }
            val loginInfo = LoginData(
                "${binding.idEd.text}",
                "${binding.passwordEd.text}"
            )

            App.prefs.loginId = loginInfo.id
            App.prefs.loginPw = loginInfo.pw

            appViewModel.login(loginInfo, baseContext)
        }

        lifecycleScope.launch(Dispatchers.Default) {
            while (true) {
                withContext(Dispatchers.Main) {
                    binding.loginBtn.setBackgroundResource(
                        if (binding.idEd.text.isEmpty() || binding.passwordEd.text.isEmpty()) R.color.gray else R.color.blue
                    )
                }
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.idEd.clearFocus()
        inputMethodManager.hideSoftInputFromWindow(binding.idEd.windowToken, 0)

        binding.passwordEd.clearFocus()
        inputMethodManager.hideSoftInputFromWindow(binding.passwordEd.windowToken, 0)

        return super.onTouchEvent(event)
    }

}