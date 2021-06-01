package com.example.pixltest.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SearchValueDataEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchValueDao(): SearchValueDao
}