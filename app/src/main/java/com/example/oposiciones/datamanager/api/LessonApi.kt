package com.example.oposiciones.datamanager.api

import com.example.oposiciones.data.Lesson
import retrofit2.http.GET

interface LessonApi {

    @GET("lessons/")
    suspend fun getLessons(): List<Lesson>

}