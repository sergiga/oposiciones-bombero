package com.example.oposiciones.datamanager.api

import com.example.oposiciones.data.Answer
import retrofit2.http.GET
import retrofit2.http.Query

interface AnswerApi {

    @GET("answers/")
    suspend fun getQuestions(@Query("lesson") lessonID: Long): List<Answer>

}