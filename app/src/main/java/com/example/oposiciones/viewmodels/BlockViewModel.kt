package com.example.oposiciones.viewmodels

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.oposiciones.data.*
import com.example.oposiciones.utils.DocumentParser
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class BlockViewModel(application: Application) : AndroidViewModel(application) {

    private val database = OposicionesDatabase.getDatabase(application)
    private val blockRepository = BlockRepository(database.blockDao())

    val allBlocks = blockRepository.allBlocks

    fun getBlockBy(id: Long) = blockRepository.getBlockBy(id)

}