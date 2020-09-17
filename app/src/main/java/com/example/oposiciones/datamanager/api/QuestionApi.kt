package com.example.oposiciones.datamanager.api

import com.example.oposiciones.data.Question
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApi {

    @GET("questions/")
    suspend fun getQuestions(@Query("lesson") lessonID: Long): List<Question>

}