package com.example.developerbuddy.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Stack::class), version = 1)
abstract class StackDatabase : RoomDatabase() {
    abstract fun stackDao(): StackDao

    companion object {
        @Volatile
        private var instance: StackDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            StackDatabase::class.java,
            "stackdatabase"
        ).build()
    }


}