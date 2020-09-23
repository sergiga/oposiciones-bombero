package com.example.oposiciones.data

import androidx.lifecycle.liveData
import com.example.oposiciones.datamanager.api.LessonApi
import com.example.oposiciones.datamanager.service.ServiceBuilder
import com.example.oposiciones.datamanager.utils.Status
import kotlinx.coroutines.Dispatchers

class LessonRepository(private val lessonDao: LessonDao) {

    fun blockLessons(blockID: Long) = lessonDao.getBlockLessons(blockID)

    fun fetchLessons() = liveData(Dispatchers.IO) {
        val lessonApi = ServiceBuilder.buildService(LessonApi::class.java)
        emit(Status.LOADING)
        try {
            val lessons = lessonApi.getLessons()
            lessonDao.insert(lessons)
            emit(Status.SUCCESS)
        } catch (exception: Exception) {
            emit(Status.ERROR)
        }
    }

}