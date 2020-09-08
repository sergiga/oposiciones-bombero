package com.example.oposiciones.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oposiciones.R
import com.example.oposiciones.fragments.LessonsFragment
import com.example.oposiciones.fragments.ToolbarFragment

const val BLOCK_ID = "com.example.oposiciones.messages.BLOCK_ID"

class LessonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val blockID = intent.getLongExtra(BLOCK_ID, 0L)
            supportFragmentManager.beginTransaction()
                .replace(R.id.toolbar, ToolbarFragment.newInstance(
                    true,
                    true,
                    R.color.text
                ))
                .replace(R.id.container, LessonsFragment.newInstance(blockID))
                .commitNow()
        }
    }

}