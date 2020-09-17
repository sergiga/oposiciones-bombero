package com.example.oposiciones.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnswerDao {

    @Query("SELECT * from answer WHERE question_id = :questionID")
    fun getQuestionAnswers(questionID: Long): LiveData<List<Answer>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(answers: List<Answer>): List<Long>

    @Query("DELETE FROM answer")
    suspend fun deleteAll()
}