package com.example.oposiciones.data

import androidx.room.*

@Entity(
    tableName = "answer",
    foreignKeys = [
        ForeignKey(entity = Question::class, parentColumns = ["id"], childColumns = ["question_id"])
    ],
    indices = [Index(value = ["letter", "question_id"], unique = true)]
)
data class Answer(
    @ColumnInfo(name = "letter") val letter: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "question_id") val questionID: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}