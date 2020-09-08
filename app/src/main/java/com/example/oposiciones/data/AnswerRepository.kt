package com.example.oposiciones.data

import androidx.lifecycle.LiveData

class AnswerRepository(private val answerDao: AnswerDao) {

    fun questionAnswers(questionID: Long): LiveData<List<Answer>>
            = answerDao.getQuestionAnswers(questionID)

    suspend fun insert(answer: Answer): Long {
        var id = answerDao.insert(answer)
        if(id == -1L) {
            return answerDao.getQuestion(answer.letter, answer.questionID).id
        }
        return id
    }
}