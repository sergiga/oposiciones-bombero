package com.example.oposiciones.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.oposiciones.R
import com.example.oposiciones.data.*
import com.example.oposiciones.utils.DocumentParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val BACK_PARAMETER = "isBackEnabled"
private const val SYNC_PARAMETER = "isSyncEnabled"
private const val COLOR_PARAMETER = "color"

private const val REQUEST_FILE = 1

class ToolbarFragment : Fragment() {

    private lateinit var database: OposicionesDatabase
    private lateinit var answerRepository: AnswerRepository
    private lateinit var blockRepository: BlockRepository
    private lateinit var lessonRepository: LessonRepository
    private lateinit var questionRepository: QuestionRepository

    private lateinit var backButton: ImageButton
    private lateinit var syncButton: ImageButton

    private var isBackEnabled: Boolean = true
    private var isSyncEnabled: Boolean = true
    private var iconColorID: Int = R.color.background

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isBackEnabled = it.getBoolean(BACK_PARAMETER)
            isSyncEnabled = it.getBoolean(SYNC_PARAMETER)
            iconColorID = it.getInt(COLOR_PARAMETER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_toolbar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindDatabase()
        bindViews()
        setupViews()
        bindListeners()
    }

    private fun bindDatabase() {
        if (activity?.application == null) return
        database = OposicionesDatabase.getDatabase(requireActivity().application)
        answerRepository = AnswerRepository(database.answerDao())
        blockRepository = BlockRepository(database.blockDao())
        lessonRepository = LessonRepository(database.lessonDao())
        questionRepository = QuestionRepository(database.questionDao())
    }

    private fun bindViews() {
        if (view == null) return
        backButton = requireView().findViewById(R.id.backButton)
        syncButton = requireView().findViewById(R.id.syncButton)
    }

    private fun setupViews() {
        context?.let {
            val color = ContextCompat.getColor(it, iconColorID)
            ImageViewCompat.setImageTintList(backButton, ColorStateList.valueOf(color))
            ImageViewCompat.setImageTintList(syncButton, ColorStateList.valueOf(color))
        }
        backButton.visibility = if (isBackEnabled) View.VISIBLE else View.GONE
        syncButton.visibility = if (isSyncEnabled) View.VISIBLE else View.GONE
    }

    private fun bindListeners() {
        backButton.setOnClickListener(onBackListener)
        syncButton.setOnClickListener(onSyncListener)
    }

    private val onBackListener = View.OnClickListener {
        if (activity != null) {
            requireActivity().onBackPressed()
        }
    }

    private val onSyncListener = View.OnClickListener {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
        }
        startActivityForResult(intent, REQUEST_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (
            requestCode != REQUEST_FILE ||
            resultCode != Activity.RESULT_OK ||
            context == null
        ) return
        data?.data?.also {
            GlobalScope.launch {
                DocumentParser(
                    answerRepository,
                    blockRepository,
                    lessonRepository,
                    questionRepository,
                    requireContext().contentResolver
                ).parseDocument(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isBackEnabled: Boolean, isSyncEnabled: Boolean, iconColor: Int) =
            ToolbarFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(BACK_PARAMETER, isBackEnabled)
                    putBoolean(SYNC_PARAMETER, isSyncEnabled)
                    putInt(COLOR_PARAMETER, iconColor)
                }
            }
    }

}