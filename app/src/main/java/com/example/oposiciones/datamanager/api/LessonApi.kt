package com.example.oposiciones.datamanager.api

import com.example.oposiciones.data.Lesson
import retrofit2.http.GET
import retrofit2.http.Query

interface LessonApi {

    @GET("lessons/")
    suspend fun getLessons(@Query("block") blockID: Long): List<Lesson>

}