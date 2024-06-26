package com.example.exerciseapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exerciseapp.utils.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_NAME)
data class HistoryEntity(
    @PrimaryKey
    val date: String
)
