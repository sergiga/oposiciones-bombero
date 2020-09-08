package com.example.oposiciones.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oposiciones.R
import com.example.oposiciones.adapters.DifficultyListAdapter
import com.example.oposiciones.data.Difficulty
import com.example.oposiciones.viewmodels.ExamViewModel
import kotlinx.android.synthetic.main.exam_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DifficultyFragment(
    private val lessonID: Long
) : Fragment() {

    companion object {
        fun newInstance(lessonID: Long) = DifficultyFragment(lessonID)
    }

    private val examViewModel: ExamViewModel by activityViewModels()

    private lateinit var difficultyListAdapter: DifficultyListAdapter
    private lateinit var difficultyRecyclerView: RecyclerView
    private lateinit var finishButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.exam_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViews()
        bindListeners()
        setupRecyclerView()
        reloadRecyclerView()
    }

    private fun bindViews() {
        difficultyRecyclerView = requireView().findViewById(R.id.recyclerView)
        finishButton = requireView().findViewById(R.id.finishButton)
        nextButton = requireView().findViewById(R.id.nextButton)
        previousButton = requireView().findViewById(R.id.previousButton)

        finishButton.visibility = View.GONE
        previousButton.visibility = View.GONE
    }

    private fun bindListeners() {
        nextButton.setOnClickListener(onSelectStartExamListener)
    }

    private fun setupRecyclerView() {
        context?.let { context ->
            var layoutManager = LinearLayoutManager(context)
            difficultyListAdapter = DifficultyListAdapter(context, onSelectDifficultyListener)
            difficultyRecyclerView.apply {
                this.adapter = difficultyListAdapter
                this.layoutManager = layoutManager
            }
        }
    }

    private fun reloadRecyclerView() {
        difficultyListAdapter.setDifficulties(examViewModel.difficulties)
    }

    private val onSelectDifficultyListener: (Difficulty) -> Unit = { difficulty ->
        examViewModel.selectDifficulty(difficulty)
        reloadRecyclerView()
    }

    private val onSelectStartExamListener = View.OnClickListener {
        GlobalScope.launch {
            examViewModel.getExamQuestions(lessonID)
            val questionID = examViewModel.getNextQuestionID()
            if (questionID != null) {
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, QuestionFragment.newInstance())
                        .commit()
                }
            }
        }
    }

}