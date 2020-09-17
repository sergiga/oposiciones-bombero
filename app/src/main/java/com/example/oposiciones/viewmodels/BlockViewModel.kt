package com.example.oposiciones.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.oposiciones.data.*

class BlockViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OposicionesDatabase.getDatabase(application)
    private val blockRepository = BlockRepository(database.blockDao())

    val blocks = blockRepository.blocks

    fun getBlockBy(id: Long) = blockRepository.getBlockBy(id)

    fun fetchBlocks() = blockRepository.fetchBlocks()

}