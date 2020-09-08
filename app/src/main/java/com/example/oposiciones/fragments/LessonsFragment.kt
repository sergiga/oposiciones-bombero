package com.example.oposiciones.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oposiciones.R
import com.example.oposiciones.activities.ExamActivity
import com.example.oposiciones.activities.LESSON_ID
import com.example.oposiciones.adapters.LessonListAdapter
import com.example.oposiciones.data.Lesson
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
    }

    private fun bindViews() {
        blockTitleTextView = requireView().findViewById(R.id.titleTextView)
        lessonRecyclerView = requireView().findViewById(R.id.recyclerView)
    }

    private fun setupLiveDataListeners() {
        blockViewModel.getBlockBy(blockID).observe(viewLifecycleOwner, Observer { block ->
            blockTitleTextView.text = block.name
        })
    }

    private fun setupRecyclerView() {
        context?.let { context ->
            var layoutManager = LinearLayoutManager(context)
            var adapter = LessonListAdapter(context, onSelectLessonListener)
            lessonRecyclerView.apply {
                this.adapter = adapter
                this.layoutManager = layoutManager
            }
            lessonViewModel.blockLessons.observe(viewLifecycleOwner, Observer { lessons ->
                adapter.setLessons(lessons)
            })
        }
    }

    private val onSelectLessonListener: (Lesson) -> Unit = { lesson ->
        val intent = Intent(context, ExamActivity::class.java).apply {
            putExtra(LESSON_ID, lesson.id)
        }
        startActivity(intent)
    }

}