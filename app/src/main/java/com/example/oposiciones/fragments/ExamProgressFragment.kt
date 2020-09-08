package com.example.oposiciones.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.oposiciones.R
import com.example.oposiciones.viewmodels.ExamViewModel

class ExamProgressFragment() : Fragment() {

    companion object {
        fun newInstance() = ExamProgressFragment()
    }

    private val examViewModel: ExamViewModel by activityViewModels()

    private lateinit var progressTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        return inflater.inflate(R.layout.exam_progress_bar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViews()
        bindLiveData()
    }

    private fun bindViews() {
        progressTextView = requireView().findViewById(R.id.progressTextView)
        progressBar = requireView().findViewById(R.id.progressBar)
    }

    private fun bindLiveData() {
        examViewModel.progress.observe(viewLifecycleOwner, Observer {
            val (current, total) = it
            view?.visibility = if (total == 0) View.GONE else View.VISIBLE
            if (total != 0) {
                progressTextView.text = "${current}/${total}"
                progressBar.progress = (current.toFloat() * 100 / total).toInt()
            }
        })
    }
}