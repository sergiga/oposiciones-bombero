package com.example.oposiciones.datamanager.api

import com.example.oposiciones.data.Answer
import retrofit2.http.GET

interface AnswerApi {

    @GET("answers/")
    suspend fun getQuestions(): List<Answer>

}