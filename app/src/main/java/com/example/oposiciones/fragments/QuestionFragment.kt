package com.example.oposiciones.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oposiciones.R
import com.example.oposiciones.adapters.QuestionListAdapter
import com.example.oposiciones.data.Answer
import com.example.oposiciones.viewmodels.ExamViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionFragment() : Fragment() {

    companion object {
        fun newInstance() = QuestionFragment()
    }

    private val examViewModel: ExamViewModel by activityViewModels()

    private lateinit var finishButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var questionDescriptionTextView: TextView
    private lateinit var questionListAdapter: QuestionListAdapter
    private lateinit var questionRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        return inflater.inflate(R.layout.exam_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViews()
        bindListeners()
        setupRecyclerView()
        setupQuestion()
    }

    private fun bindViews() {
        finishButton = requireView().findViewById(R.id.finishButton)
        nextButton = requireView().findViewById(R.id.nextButton)
        previousButton = requireView().findViewById(R.id.previousButton)
        questionRecyclerView = requireView().findViewById(R.id.recyclerView)
        questionDescriptionTextView = requireView().findViewById(R.id.titleTextView)
    }

    private fun bindListeners() {
        finishButton.setOnClickListener(onSelectFinishExamListener)
        nextButton.setOnClickListener(onSelectNextQuestionListener)
        previousButton.setOnClickListener(onSelectPreviousQuestionListener)
    }

    private fun setupRecyclerView() {
        context?.let { context ->
            val layoutManager = LinearLayoutManager(context)
            questionListAdapter = QuestionListAdapter(context, onSelectAnswerListener)
            questionRecyclerView.apply {
                this.adapter = questionListAdapter
                this.layoutManager = layoutManager
            }
        }
    }

    private fun setupQuestion() {
        val question = examViewModel.getCurrentQuestion()
        questionDescriptionTextView.text = question.question.description
        finishButton.visibility = if (examViewModel.isFinishButtonVisible()) View.VISIBLE else View.GONE
        nextButton.visibility = if (examViewModel.isNextButtonVisible()) View.VISIBLE else View.GONE
        previousButton.visibility = if (examViewModel.isPreviousButtonVisible()) View.VISIBLE else View.GONE
        reloadRecyclerView()
    }

    private fun reloadRecyclerView() {
        questionListAdapter.setQuestion(examViewModel.getCurrentQuestion())
    }

    private val onSelectAnswerListener: (Answer) -> Unit = { answer ->
        examViewModel.selectAnswer(answer)
        reloadRecyclerView()
    }

    private val onSelectNextQuestionListener = View.OnClickListener {
        val questionID = examViewModel.getNextQuestionID()
        if (questionID != null) {
            setupQuestion()
        }
    }

    private val onSelectPreviousQuestionListener = View.OnClickListener {
        val questionID = examViewModel.getPreviousQuestionID()
        if (questionID != null) {
            setupQuestion()
        }
    }

    private val onSelectFinishExamListener = View.OnClickListener {
        lifecycleScope.launch {
            whenStarted {
                withContext(Dispatchers.IO) {
                    examViewModel.finishExam()
                }
                requireActivity().onBackPressed()
            }
        }
    }

}