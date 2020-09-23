package com.example.oposiciones.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.oposiciones.R
import com.example.oposiciones.activities.ExamActivity
import com.example.oposiciones.activities.LESSON_ID
import com.example.oposiciones.adapters.LessonListAdapter
import com.example.oposiciones.data.Lesson
import com.example.oposiciones.datamanager.utils.Status
import com.example.oposiciones.viewmodels.BlockViewModel
import com.example.oposiciones.viewmodels.LessonViewModel
import com.example.oposiciones.viewmodels.LessonViewModelFactory

class LessonsFragment(
    private val blockID: Long
) : Fragment() {

    companion object {
        fun newInstance(blockID: Long) = LessonsFragment(blockID)
    }

    private lateinit var blockViewModel: BlockViewModel
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var lessonViewModelFactory: LessonViewModelFactory

    private lateinit var blockTitleTextView: TextView
    private lateinit var lessonRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        blockViewModel = ViewModelProvider(this).get(BlockViewModel::class.java)
        lessonViewModelFactory = LessonViewModelFactory(requireActivity().application, blockID)
        lessonViewModel = ViewModelProvider(this, lessonViewModelFactory)
            .get(LessonViewModel::class.java)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViews()
        setupLiveDataListeners()
        setupRecyclerView()
        fetchLessons()
    }

    private fun bindViews() {
        blockTitleTextView = requireView().findViewById(R.id.titleTextView)
        lessonRecyclerView = requireView().findViewById(R.id.recyclerView)
        swipeRefreshLayout = requireView().findViewById(R.id.swipeRefreshLayout)
    }

    private fun setupLiveDataListeners() {
        blockViewModel.getBlockBy(blockID).observe(viewLifecycleOwner, Observer { block ->
            blockTitleTextView.text = block.description
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = LessonListAdapter(requireContext(), onSelectLessonListener)
        lessonRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
        lessonViewModel.lessons.observe(viewLifecycleOwner, Observer { lessons ->
            adapter.setLessons(lessons)
        })
        swipeRefreshLayout.setOnRefreshListener {
            fetchLessons()
        }
    }

    private val onSelectLessonListener: (Lesson) -> Unit = { lesson ->
        fetchQuestions(lesson.id)
    }

    private fun fetchLessons() {
        lessonViewModel.fetchLessons().observe(viewLifecycleOwner, Observer {
            when (it) {
                Status.ERROR -> swipeRefreshLayout.isRefreshing = false
                Status.SUCCESS -> swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun fetchQuestions(lessonID: Long) {
        lessonViewModel.fetchQuestions().observe(viewLifecycleOwner, Observer{
            when (it) {
                Status.ERROR -> fetchAnswers(lessonID)
                Status.SUCCESS -> fetchAnswers(lessonID)
            }
        })
    }

    private fun fetchAnswers(lessonID: Long) {
        lessonViewModel.fetchAnswers().observe(viewLifecycleOwner, Observer{
            when (it) {
                Status.ERROR -> startExamActivity(lessonID)
                Status.SUCCESS -> startExamActivity(lessonID)
            }
        })
    }

    private fun startExamActivity(lessonID: Long) {
        val intent = Intent(context, ExamActivity::class.java).apply {
            putExtra(LESSON_ID, lessonID)
        }
        startActivity(intent)
    }

}