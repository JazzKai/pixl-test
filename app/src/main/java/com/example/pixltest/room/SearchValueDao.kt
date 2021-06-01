package com.example.pixltest.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pixltest.model.SearchImageResponse

@Dao
interface SearchValueDao {
    @Query("SELECT * FROM searchvaluedataentity")
    fun getAll(): List<SearchValueDataEntity>

    @Query("SELECT * FROM searchvaluedataentity WHERE uid IN (:id)")
    fun loadAllByIds(id: String): List<SearchValueDataEntity>

    @Insert
    fun insertAll(vararg values: SearchValueDataEntity)

    @Delete
    fun delete(values: SearchValueDataEntity)
}