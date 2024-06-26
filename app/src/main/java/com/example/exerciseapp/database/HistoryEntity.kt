package com.example.exerciseapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exerciseapp.utils.AppConstants

@Entity(tableName = AppConstants.TABLE_NAME)
data class HistoryEntity(
    @PrimaryKey
    val date: String
)
