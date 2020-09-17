package com.example.oposiciones.data

import androidx.lifecycle.liveData
import com.example.oposiciones.datamanager.api.AnswerApi
import com.example.oposiciones.datamanager.service.ServiceBuilder
import com.example.oposiciones.datamanager.utils.Status
import kotlinx.coroutines.Dispatchers

class AnswerRepository(private val answerDao: AnswerDao) {

    fun fetchAnswers(lessonID: Long) = liveData(Dispatchers.IO) {
        val answerApi = ServiceBuilder.buildService(AnswerApi::class.java)
        emit(Status.LOADING)
        try {
            val questions = answerApi.getQuestions(lessonID)
            answerDao.insert(questions)
            emit(Status.SUCCESS)
        } catch (exception: Exception) {
            emit(Status.ERROR)
        }
    }

}