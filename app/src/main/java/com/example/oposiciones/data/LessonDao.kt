package com.example.oposiciones.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LessonDao {

    @Query("""
    SELECT lesson.*, SUM(question.total_hits) as totalHits, SUM(question.total_answers) as totalAnswers
    FROM lesson
    LEFT JOIN question ON lesson.id = question.lesson_id
    WHERE lesson.block_id = :blockID
    ORDER BY lesson.name
    """)
    fun getBlockLessons(blockID: Long): LiveData<List<Lesson>>

    @Query("""
    SELECT *, SUM(question.total_hits) as totalHits, SUM(question.total_answers)
    FROM lesson
    LEFT JOIN question ON lesson.id = question.lesson_id
    WHERE block_id = :blockID AND name = :name
    """)
    suspend fun getLessonByName(name: String, blockID: Long): Lesson

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(lesson: Lesson): Long

    @Query("DELETE FROM lesson")
    suspend fun deleteAll()

}