package com.example.exerciseapp.features

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.exerciseapp.ExerciseApp
import com.example.exerciseapp.database.HistoryDao
import com.example.exerciseapp.database.HistoryEntity
import com.example.exerciseapp.databinding.ActivitySuccessBinding
import kotlinx.coroutines.launch
import java.util.*

class SuccessActivity : AppCompatActivity() {
    private var binding: ActivitySuccessBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setupActionBar()
        setupNavigation()
        setupFinishButton()

        val historyDao: HistoryDao = (application as ExerciseApp).database.historyDao()
        addDateToDatabase(historyDao)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarSuccessActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupNavigation() {
        binding?.toolbarSuccessActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupFinishButton() {
        binding?.finishButton?.setOnClickListener {
            finish()
        }
    }

    private fun addDateToDatabase(historyDao: HistoryDao) {
        val date: String = getCurrentDate()
        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("ROOM", "Date added: $date")
        }
    }

    private fun getCurrentDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        val dateTime: Date = calendar.time
        val formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        return formatter.format(dateTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}