package com.example.exerciseapp.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerciseapp.*
import com.example.exerciseapp.adapters.HistoryAdapter
import com.example.exerciseapp.ExerciseApp
import com.example.exerciseapp.database.HistoryDao
import com.example.exerciseapp.database.HistoryEntity
import com.example.exerciseapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var historyBinding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        historyBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(historyBinding!!.root)

        setSupportActionBar(historyBinding?.historyToolbar)

        if (supportActionBar != null) {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = getString(R.string.history_title)
            }
        }

        historyBinding?.historyToolbar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val historyDao: HistoryDao = (application as ExerciseApp).database.historyDao()

        getAllCompletedDates(historyDao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { historyEntities ->
                if (historyEntities.isNotEmpty()) {
                    historyBinding?.apply {
                        historyTitle.visibility = View.VISIBLE
                        historyRecyclerView.visibility = View.VISIBLE
                        noDataAvailableText.visibility = View.INVISIBLE

                        historyRecyclerView.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    }
                    val dateList = ArrayList<String>()

                    for (entity: HistoryEntity in historyEntities) dateList.add(entity.date)

                    val historyAdapter = HistoryAdapter(dateList)

                    historyBinding?.historyRecyclerView?.adapter = historyAdapter

                } else {
                    historyBinding?.apply {
                        historyTitle.visibility = View.GONE
                        historyRecyclerView.visibility = View.GONE
                        noDataAvailableText.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        historyBinding = null
    }
}