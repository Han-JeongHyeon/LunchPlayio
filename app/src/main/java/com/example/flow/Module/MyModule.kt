package com.example.flow.Module

import android.app.Application
import androidx.room.Room
import com.example.flow.AppViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    single { RetrofitObject( get() ) }
}

var viewModule = module{
    viewModel{
        AppViewModel( get() )
    }
}