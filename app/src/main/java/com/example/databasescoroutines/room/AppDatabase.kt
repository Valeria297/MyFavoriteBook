package com.example.databasescoroutines.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.databasescoroutines.model.RoomBook

@Database(entities = [RoomBook::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomBookDao(): RoomBookDao
}