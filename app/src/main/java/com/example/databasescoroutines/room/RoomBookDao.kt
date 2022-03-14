package com.example.databasescoroutines.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.databasescoroutines.model.RoomBook

@Dao
interface RoomBookDao {
    @Query("SELECT * FROM roombook")
    fun getAll(): List<RoomBook>

    @Insert
    fun insertBooks(vararg books: RoomBook)

    @Delete
    fun delete(book: RoomBook)
}