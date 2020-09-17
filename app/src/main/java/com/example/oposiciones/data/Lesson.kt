package com.example.oposiciones.data

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "lesson",
    foreignKeys = [
        ForeignKey(entity = Block::class, parentColumns = ["id"], childColumns = ["block_id"])
    ],
    indices = [Index(value = ["description", "block_id"], unique = true)]
)
data class Lesson(
    @PrimaryKey() @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "block_id") @SerializedName("block") val blockID: Long,
    val totalAnswers: Long = 0,
    val totalHits: Long = 0
) {

    @Ignore
    var globalScore: Float? = null
        get() {
            if (totalAnswers == 0L) { return null }
            return (totalHits * 100) / (totalAnswers * 1f)
        }
}
