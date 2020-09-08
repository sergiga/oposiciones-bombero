package com.example.oposiciones.data

import androidx.room.*

@Entity(
    tableName = "lesson",
    foreignKeys = [
        ForeignKey(entity = Block::class, parentColumns = ["id"], childColumns = ["block_id"])
    ],
    indices = [Index(value = ["name", "block_id"], unique = true)]
)
data class Lesson(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "block_id") val blockID: Long,
    val totalAnswers: Long = 0,
    val totalHits: Long = 0
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @Ignore
    var globalScore: Float? = null
        get() {
            if (totalAnswers == 0L) { return null }
            return (totalHits * 100) / (totalAnswers * 1f)
        }
}
