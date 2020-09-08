package com.example.oposiciones.data

class LessonRepository(private val lessonDao: LessonDao) {

    fun blockLessons(blockID: Long) = lessonDao.getBlockLessons(blockID)

    suspend fun insert(lesson: Lesson): Long {
        val id = lessonDao.insert(lesson)
        if(id == -1L) {
            return lessonDao.getLessonByName(lesson.name, lesson.blockID).id
        }
        return id
    }

}