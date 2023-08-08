package com.example.cocktailbar

import android.app.Application
import androidx.room.Room
import com.example.cocktailbar.room.AppDatabase

class App : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "db").build()
    }
}