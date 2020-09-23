package com.example.oposiciones.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.oposiciones.data.AnswerRepository
import com.example.oposiciones.data.LessonRepository
import com.example.oposiciones.data.OposicionesDatabase
import com.example.oposiciones.data.QuestionRepository

class LessonViewModel(application: Application, private val blockID: Long) : AndroidViewModel(application) {

    private val lessonRepository = LessonRepository(OposicionesDatabase.getDatabase(application).lessonDao())
    private val questionRepository = QuestionRepository(OposicionesDatabase.getDatabase(application).questionDao())
    private val answerRepository = AnswerRepository(OposicionesDatabase.getDatabase(application).answerDao())

    val lessons = lessonRepository.blockLessons(blockID)

    fun fetchLessons() = lessonRepository.fetchLessons()

    fun fetchQuestions() = questionRepository.fetchQuestions()

    fun fetchAnswers() = answerRepository.fetchAnswers()

}