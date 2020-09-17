package com.example.oposiciones.data

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "answer",
    foreignKeys = [
        ForeignKey(entity = Question::class, parentColumns = ["id"], childColumns = ["question_id"])
    ]
)
data class Answer(
    @PrimaryKey() @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "correct") val correct: Boolean,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "question_id") @SerializedName("question") val questionID: Long
)