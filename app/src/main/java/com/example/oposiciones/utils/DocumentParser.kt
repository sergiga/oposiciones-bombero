package com.example.oposiciones.utils

import android.content.ContentResolver
import android.net.Uri
import com.example.oposiciones.data.*
import java.io.BufferedReader
import java.io.InputStreamReader

private const val BLOCK_LINE: Int = 0
private const val LESSON_LINE: Int = 1
private const val DIFFICULTY_LINE: Int = 2

class DocumentParser(
    private val answerRepository: AnswerRepository,
    private val blockRepository: BlockRepository,
    private val lessonRepository: LessonRepository,
    private val questionRepository: QuestionRepository,
    private val resolver: ContentResolver
) {

    suspend fun parseDocument(uri: Uri) {
        val inputStream = resolver.openInputStream(uri)
        val br = BufferedReader(InputStreamReader(inputStream))
        readDocument(br)
    }

    private suspend fun readDocument(br: BufferedReader) {
        var line = 0
        var blockID: Long = 0
        var lessonID: Long = 0
        var questionID: Long = 0
        var difficulty: Int = 1
        while (br.ready()) {
            val text = br.readLine().trim()
            when(line) {
                BLOCK_LINE -> {
                    val block = Block(text)
                    blockID = blockRepository.insert(block)
                }
                LESSON_LINE -> {
                    val lesson = Lesson(text, blockID)
                    lessonID = lessonRepository.insert(lesson)
                }
                DIFFICULTY_LINE -> {
                    difficulty = text.toInt()
                }
                else -> {
                    val questionRE = """^(\d+)\s*\.\s*(.*)$""".toRegex()
                    val answerRE = """^([a-e|A-E]+)\s*\)\s*(.*)$""".toRegex()
                    val correctRE = """^(\d+)\s*-\s*([a-e|A-E])\s*\.?\s*(.*)?$""".toRegex()
                    if (questionRE.find(text) != null) {
                        val (number, description) = questionRE.find(text)!!.destructured
                        val question = Question(number.toLong(), description.trim(), difficulty, lessonID)
                        questionID = questionRepository.insert(question)
                    } else if (answerRE.find(text) != null) {
                        val (letter, description) = answerRE.find(text)!!.destructured
                        val answer = Answer(letter.trim().toLowerCase(), description.trim(), questionID)
                        answerRepository.insert(answer)
                    } else if (correctRE.find(text) != null) {
                        val (number, letter, tip) = correctRE.find(text)!!.destructured
                        questionRepository.updateQuestionAnswer(letter.trim().toLowerCase(), tip, number.toLong(), lessonID)
                    }
                }
            }
            line++
        }
    }
}