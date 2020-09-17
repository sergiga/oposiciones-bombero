package com.example.oposiciones.data

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "question",
    foreignKeys = [
        ForeignKey(entity = Lesson::class, parentColumns = ["id"], childColumns = ["lesson_id"])
    ],
    indices = [Index(value = ["description", "lesson_id"], unique = true)]
)
data class Question (
    @PrimaryKey() @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "lesson_id") @SerializedName("lesson") val lessonID: Long,
    @ColumnInfo(name = "tip") val tip: String?
) {

    @ColumnInfo(name = "total_answers")
    var totalAnswers: Long = 0

    @ColumnInfo(name = "total_hits")
    var totalHits: Long = 0

    @Ignore
    var selectedAnswerID: Long? = null
}

data class QuestionWithAnswers(
    @Embedded val entity: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "question_id"
    )
    val answers: List<Answer>
) {

    var correctAnswerID: Long? = null
        get() {
            return answers.find { it.correct }?.id
        }

}