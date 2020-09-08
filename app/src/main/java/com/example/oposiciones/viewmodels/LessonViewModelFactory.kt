package com.example.oposiciones.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class LessonViewModelFactory(private val application: Application, private val blockID: Long): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            return LessonViewModel(application, blockID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}