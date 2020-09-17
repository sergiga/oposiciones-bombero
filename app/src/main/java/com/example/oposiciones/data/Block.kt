package com.example.oposiciones.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "block",
    indices = [Index(value = ["description"], unique = true)]
)
data class Block(
    @PrimaryKey() @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "description") val description: String
)
