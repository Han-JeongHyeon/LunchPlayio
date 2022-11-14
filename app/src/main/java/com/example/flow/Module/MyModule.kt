package com.example.flow.Module

import android.app.Application
import androidx.room.Room
import com.example.flow.AppViewModel
import com.example.flow.Room.Dao
import com.example.flow.Room.Database
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    fun lunchDatabase(application: Application) : Database {
        return Room.databaseBuilder(application, Database::class.java, "fish")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun lunchDao(database: Database) : Dao {
        return database.lunchInfo()
    }

    single { lunchDatabase( androidApplication() ) }
    single { lunchDao( get() ) }

    single { RetrofitObject( get() ) }
    single { Repository( get() ) }
}

var viewModule = module{
    viewModel{
        AppViewModel( get() )
    }
}