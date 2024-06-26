package com.example.exerciseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exerciseapp.databinding.ActivityMainBinding
import com.example.exerciseapp.features.BMIActivity
import com.example.exerciseapp.features.ExerciseActivity
import com.example.exerciseapp.features.HistoryActivity

class MainActivity : AppCompatActivity() {
    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding!!.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        activityMainBinding?.flStart?.setOnClickListener {
            startActivity(Intent(this, ExerciseActivity::class.java))
        }

        activityMainBinding?.flBMI?.setOnClickListener {
            startActivity(Intent(this, BMIActivity::class.java))
        }

        activityMainBinding?.flHistory?.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityMainBinding = null
    }
}