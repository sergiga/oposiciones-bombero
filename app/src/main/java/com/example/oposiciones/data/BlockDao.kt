package com.example.oposiciones.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BlockDao {

    @Query("SELECT * from block ORDER BY name ASC")
    fun getBlocks(): LiveData<List<Block>>

    @Query("SELECT * from block WHERE id = :id")
    fun getBlockBy(id: Long): LiveData<Block>

    @Query("SELECT * from block WHERE name = :name")
    suspend fun getBlockByName(name: String): Block

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(block: Block): Long

    @Query("DELETE FROM block")
    suspend fun deleteAll()

}