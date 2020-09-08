package com.example.oposiciones.data

import androidx.lifecycle.LiveData

class BlockRepository(private val blockDao: BlockDao) {

    val allBlocks: LiveData<List<Block>> = blockDao.getBlocks()

    fun getBlockBy(id: Long): LiveData<Block> = blockDao.getBlockBy(id)

    suspend fun insert(block: Block): Long {
        val id = blockDao.insert(block)
        if (id == -1L) {
            return blockDao.getBlockByName(block.name).id
        }
        return id
    }

}