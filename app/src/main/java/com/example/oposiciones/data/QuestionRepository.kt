package com.example.oposiciones.data

import com.example.oposiciones.constants.Constants
import kotlin.math.min

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun getExamQuestions(lessonID: Long, difficulty: Long) : List<QuestionWithAnswers> {
        val questions = questionDao.getQuestions(lessonID, difficulty)
        print(questions.size)
        return questions.shuffled().slice(0..min(questions.size - 1, Constants.EXAM_QUESTION_COUNT - 1))
    }

    private suspend fun getQuestion(number: Long, lessonID: Long)
            = questionDao.getQuestion(number, lessonID)

    suspend fun updateQuestionAnswer(
        answer: String,
        tip: String?,
        number: Long,
        lessonID: Long
    ) = questionDao.updateQuestionAnswer(answer, tip, number, lessonID)

    suspend fun insert(question: Question): Long {
        val id = questionDao.insert(question)
        if(id == -1L) {
            return getQuestion(question.number, question.lessonID).id
        }
        return id
    }

    suspend fun finishExam(questions: List<QuestionWithAnswers>) {
        for (question in questions) {
            question.question.totalAnswers++
            if (question.question.answer == question.question.selectedAnswer) {
                question.question.totalHits++
            }
        }
        questionDao.updateQuestions(questions.map { it.question })
    }

}