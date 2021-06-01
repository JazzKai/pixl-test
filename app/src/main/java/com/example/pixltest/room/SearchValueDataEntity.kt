package com.example.pixltest.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pixltest.model.SearchImageResponse

@Entity
data class SearchValueDataEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "value") val value: String?
)
