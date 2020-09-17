package com.example.oposiciones.data

import androidx.lifecycle.liveData
import com.example.oposiciones.constants.Constants
import com.example.oposiciones.datamanager.api.LessonApi
import com.example.oposiciones.datamanager.api.QuestionApi
import com.example.oposiciones.datamanager.service.ServiceBuilder
import com.example.oposiciones.datamanager.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlin.math.min

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun getExamQuestions(lessonID: Long, difficulty: Long) : List<QuestionWithAnswers> {
        val questions = questionDao.getQuestions(lessonID, difficulty)
        print(questions.size)
        return questions.shuffled().slice(0..min(questions.size - 1, Constants.EXAM_QUESTION_COUNT - 1))
    }

    fun fetchQuestions(lessonID: Long) = liveData(Dispatchers.IO) {
        val questionApi = ServiceBuilder.buildService(QuestionApi::class.java)
        emit(Status.LOADING)
        try {
            val questions = questionApi.getQuestions(lessonID)
            questionDao.insert(questions)
            emit(Status.SUCCESS)
        } catch (exception: Exception) {
            emit(Status.ERROR)
        }
    }

    suspend fun finishExam(questions: List<QuestionWithAnswers>) {
        for (question in questions) {
            question.entity.totalAnswers++
            if (question.entity.selectedAnswerID == question.correctAnswerID) {
                question.entity.totalHits++
            }
        }
        questionDao.updateQuestions(questions.map { it.entity })
    }

}