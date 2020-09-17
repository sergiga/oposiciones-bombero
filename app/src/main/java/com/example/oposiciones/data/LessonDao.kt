package com.example.oposiciones.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LessonDao {

    @Query("""
    SELECT lesson.*, qst.total_answers as totalAnswers, qst.total_hits as totalHits
    FROM lesson
    LEFT JOIN (
        SELECT lesson_id, SUM(total_answers) as total_answers, SUM(total_hits) as total_hits
        FROM question
        GROUP BY lesson_id
    ) qst ON lesson.id = qst.lesson_id
    WHERE lesson.block_id = :blockID
    """)
    fun getBlockLessons(blockID: Long): LiveData<List<Lesson>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(lesson: List<Lesson>): List<Long>

    @Query("DELETE FROM lesson")
    suspend fun deleteAll()

}