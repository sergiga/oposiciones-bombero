package com.example.oposiciones.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnswerDao {

    @Query("SELECT * from answer WHERE question_id = :questionID ORDER BY letter ASC")
    fun getQuestionAnswers(questionID: Long): LiveData<List<Answer>>

    @Query("SELECT * from answer WHERE question_id = :questionID AND letter = :letter")
    suspend fun getQuestion(letter: String, questionID: Long): Answer

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(answer: Answer): Long

    @Query("DELETE FROM answer")
    suspend fun deleteAll()
}