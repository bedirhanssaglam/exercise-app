package com.example.exerciseapp

import android.app.Application
import com.example.exerciseapp.database.HistoryDatabase

class ExerciseApp : Application() {
    val database by lazy {
        HistoryDatabase.getInstance(this)
    }
}