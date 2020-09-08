package com.example.oposiciones.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Answer::class,
        Block::class,
        Lesson::class,
        Question::class
    ],
    version = 1,
    exportSchema = false
)
abstract class OposicionesDatabase : RoomDatabase() {

    abstract fun answerDao(): AnswerDao
    abstract fun blockDao(): BlockDao
    abstract fun lessonDao(): LessonDao
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var INSTANCE: OposicionesDatabase? = null

        fun getDatabase(context: Context): OposicionesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OposicionesDatabase::class.java,
                    "oposiciones_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}