package com.example.oposiciones.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oposiciones.R
import com.example.oposiciones.fragments.MainFragment
import com.example.oposiciones.fragments.ToolbarFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.toolbar, ToolbarFragment.newInstance(
                    false,
                    true,
                    R.color.text
                ))
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}