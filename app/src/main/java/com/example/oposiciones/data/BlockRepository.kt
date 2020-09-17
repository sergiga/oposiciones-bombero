package com.example.oposiciones.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.oposiciones.datamanager.api.BlockApi
import com.example.oposiciones.datamanager.service.ServiceBuilder
import com.example.oposiciones.datamanager.utils.Status
import kotlinx.coroutines.Dispatchers

class BlockRepository(private val blockDao: BlockDao) {

    val blocks = blockDao.getBlocks()

    fun getBlockBy(id: Long) = blockDao.getBlockBy(id)

    fun fetchBlocks() = liveData(Dispatchers.IO) {
        val blockAPI = ServiceBuilder.buildService(BlockApi::class.java)
        emit(Status.LOADING)
        try {
            val blocks = blockAPI.getBlocks()
            blockDao.insert(blocks)
            emit(Status.SUCCESS)
        } catch (exception: Exception) {
            emit(Status.ERROR)
        }
    }

}