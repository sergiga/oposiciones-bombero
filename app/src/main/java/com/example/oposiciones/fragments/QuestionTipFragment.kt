package com.example.oposiciones.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.oposiciones.R
import com.example.oposiciones.viewmodels.ExamViewModel

class QuestionTipFragment() : Fragment() {

    companion object {
        fun newInstance() = QuestionTipFragment()
    }

    private val examViewModel: ExamViewModel by activityViewModels()

    private lateinit var questionTipTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        return inflater.inflate(R.layout.exam_tip_bar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViews()
        bindLiveData()
    }

    private fun bindViews() {
        questionTipTextView = requireView().findViewById(R.id.tipTextView)
    }

    private fun bindLiveData() {
        examViewModel.tip.observe(viewLifecycleOwner, Observer {
            questionTipTextView.text = it
        })
    }

}