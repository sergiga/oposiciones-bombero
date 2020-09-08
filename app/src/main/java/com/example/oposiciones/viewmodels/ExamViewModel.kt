package com.example.oposiciones.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.oposiciones.data.*

class ExamViewModel (application: Application) : AndroidViewModel(application) {

    private val questionRepository =
        QuestionRepository(OposicionesDatabase.getDatabase(application).questionDao())

    private var currentQuestionIndex: Int? = null
    private var selectedDifficulty: Difficulty? = null
    private var selectedQuestions = emptyList<QuestionWithAnswers>()

    var difficulties = listOf(
        Difficulty(1, "Fácil", false),
        Difficulty(2, "Intermedio", false),
        Difficulty(3, "Difícil", false)
    )

    val progress: MutableLiveData<Pair<Int, Int>> by lazy {
        MutableLiveData<Pair<Int, Int>>(Pair(0, 0))
    }

    val tip: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    fun selectDifficulty(difficulty: Difficulty) {
        difficulties.forEach {
            it.selected = false
        }
        difficulty.selected = true
        selectedDifficulty = difficulty
    }

    fun selectAnswer(answer: Answer) {
        val question = selectedQuestions[currentQuestionIndex!!].question
        if (question.selectedAnswer != null) return
        question.selectedAnswer = answer.letter
        progress.value = Pair(
            selectedQuestions.filter { it.question.selectedAnswer != null }.size,
            selectedQuestions.size
        )
        tip.value = question.tip
    }

    fun getNextQuestionID(): Long? {
        if (selectedQuestions.isEmpty()) { return null }
        currentQuestionIndex = if (currentQuestionIndex == null) 0 else { currentQuestionIndex!! + 1 }
        val question = selectedQuestions[currentQuestionIndex!!]
        return question.question.id
    }

    fun getPreviousQuestionID(): Long? {
        if (selectedQuestions.isEmpty()) { return null }
        currentQuestionIndex = if (currentQuestionIndex == null) 0 else { currentQuestionIndex!! - 1 }
        val question = selectedQuestions[currentQuestionIndex!!]
        return question.question.id
    }

    fun getCurrentQuestion(): QuestionWithAnswers {
        return selectedQuestions[currentQuestionIndex!!]
    }

    fun isPreviousButtonVisible(): Boolean {
        return currentQuestionIndex != null && currentQuestionIndex != 0
    }

    fun isNextButtonVisible(): Boolean {
        if (currentQuestionIndex == null) { return false }
        return selectedQuestions.size != currentQuestionIndex!! + 1
    }

    fun isFinishButtonVisible(): Boolean {
        if (currentQuestionIndex == null) { return false }
        return selectedQuestions.size == currentQuestionIndex!! + 1
    }

    suspend fun getExamQuestions(lessonID: Long) {
        if (selectedDifficulty == null) return
        selectedQuestions = questionRepository.getExamQuestions(lessonID, selectedDifficulty!!.id)
        progress.postValue(Pair(0, selectedQuestions.size))
    }

    suspend fun finishExam() {
        questionRepository.finishExam(selectedQuestions)
    }
}