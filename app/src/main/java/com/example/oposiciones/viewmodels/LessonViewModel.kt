package com.example.oposiciones.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.oposiciones.data.Lesson
import com.example.oposiciones.data.LessonRepository
import com.example.oposiciones.data.OposicionesDatabase

class LessonViewModel(application: Application, blockID: Long) : AndroidViewModel(application) {

    private val lessonRepository = LessonRepository(OposicionesDatabase.getDatabase(application).lessonDao())

    val blockLessons = lessonRepository.blockLessons(blockID)

}