package com.example.developerbuddy.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StackDao {

    @Insert
    suspend fun insertAll(vararg stacks: Stack): List<Long>

    @Query("SELECT * FROM stack")
    suspend fun getAllStacks(): List<Stack>

    @Query("SELECT * FROM stack WHERE uuid = :itemId")
    suspend fun getStack(itemId: Int): Stack

    @Query("DELETE FROM stack")
    suspend fun deleteAllStacks()
}