package com.example.oposiciones.data

import androidx.room.*

@Entity(
    tableName = "question",
    foreignKeys = [
        ForeignKey(entity = Lesson::class, parentColumns = ["id"], childColumns = ["lesson_id"])
    ],
    indices = [Index(value = ["description", "lesson_id"], unique = true)]
)
data class Question (
    @ColumnInfo(name = "number") val number: Long,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "lesson_id") val lessonID: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "answer")
    var answer: String? = null

    @ColumnInfo(name = "tip")
    var tip: String? = null

    @ColumnInfo(name = "total_answers")
    var totalAnswers: Long = 0

    @ColumnInfo(name = "total_hits")
    var totalHits: Long = 0

    @Ignore
    var selectedAnswer: String? = null
}

data class QuestionWithAnswers(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "question_id"
    )
    val answers: List<Answer>
)