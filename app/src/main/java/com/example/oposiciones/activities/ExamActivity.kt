package com.example.oposiciones.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oposiciones.R
import com.example.oposiciones.fragments.DifficultyFragment
import com.example.oposiciones.fragments.ExamProgressFragment
import com.example.oposiciones.fragments.QuestionTipFragment
import com.example.oposiciones.fragments.ToolbarFragment

const val LESSON_ID = "com.example.oposiciones.messages.LESSON_ID"

class ExamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exam_activity)
        if (savedInstanceState == null) {
            val lessonID = intent.getLongExtra(LESSON_ID, 0L)
            supportFragmentManager.beginTransaction()
                .replace(R.id.toolbar, ToolbarFragment.newInstance(
                    true,
                    false,
                    R.color.background
                ))
                .replace(R.id.progressBar, ExamProgressFragment.newInstance())
                .replace(R.id.container, DifficultyFragment.newInstance(lessonID))
                .replace(R.id.tipBar, QuestionTipFragment())
                .commitNow()
        }
    }

}