package com.example.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flow.Adapter.Adapter
import com.example.flow.Data.CheckList
import com.example.flow.Data.Data
import com.example.flow.Data.GetTableData
import com.example.flow.Module.RetrofitObject
import com.example.flow.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var dateAdapter: Adapter? = null

    private val appViewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val userName = intent.getStringExtra("userName")

        binding.userName.text = userName

        dateAdapter = Adapter()

        appViewModel.getTableDate("$id")

        setAdapter()
        setObserver()
    }

    private fun setAdapter(){
        binding.viewModel = appViewModel

        dateAdapter = Adapter().apply {
            setOnCheckedChangeListener(object : Adapter.OnCheckedChangeListener {
                override fun onItemCheck(v: View, item: GetTableData, isChecked: Boolean) {
                    appViewModel.getLunchChecked(item, isChecked)
                }
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dateAdapter
        }

    }

    private fun setObserver(){
        lifecycleScope.launchWhenStarted {
            binding.recyclerView.visibility = View.VISIBLE
            appViewModel.lunchList.collectLatest {
                dateAdapter!!.submitList(it)
            }
        }

    }

}