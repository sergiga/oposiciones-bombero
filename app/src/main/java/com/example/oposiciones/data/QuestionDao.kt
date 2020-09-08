package com.example.oposiciones.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {

    @Query("SELECT * from question WHERE lesson_id = :lessonID")
    fun getLessonQuestions(lessonID: Long): LiveData<List<Question>>

    @Transaction
    @Query("SELECT * from question WHERE lesson_id = :lessonID AND difficulty = :difficulty")
    suspend fun getQuestions(lessonID: Long, difficulty: Long): List<QuestionWithAnswers>

    @Query("SELECT * from question WHERE number = :number AND lesson_id = :lessonID")
    suspend fun getQuestion(number: Long, lessonID: Long): Question

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(question: Question): Long

    @Update
    suspend fun updateQuestions(questions: List<Question>)

    @Query("""
        UPDATE question
        SET answer = :answer, tip = :tip
        WHERE number = :number AND lesson_id = :lessonID
    """)
    suspend fun updateQuestionAnswer(answer: String, tip: String?, number: Long, lessonID: Long)

    @Query("DELETE FROM question")
    suspend fun deleteAll()


}