package com.ksa.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do")
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: String,
    var title: String,
    var priority: Priority,
    var description: String
)
