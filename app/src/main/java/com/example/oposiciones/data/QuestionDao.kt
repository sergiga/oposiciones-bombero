package com.example.oposiciones.data

import androidx.room.*

@Dao
interface QuestionDao {

    @Transaction
    @Query("SELECT * from question WHERE lesson_id = :lessonID AND difficulty = :difficulty")
    suspend fun getQuestions(lessonID: Long, difficulty: Long): List<QuestionWithAnswers>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(questions: List<Question>): List<Long>

    @Update
    suspend fun updateQuestions(questions: List<Question>)

    @Query("DELETE FROM question")
    suspend fun deleteAll()


}