package com.example.oposiciones.datamanager.api

import com.example.oposiciones.data.Question
import retrofit2.http.GET

interface QuestionApi {

    @GET("questions/")
    suspend fun getQuestions(): List<Question>

}