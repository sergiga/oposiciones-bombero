package com.example.oposiciones.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.oposiciones.R

private const val BACK_PARAMETER = "isBackEnabled"
private const val SYNC_PARAMETER = "isSyncEnabled"
private const val COLOR_PARAMETER = "color"

private const val REQUEST_FILE = 1

class ToolbarFragment : Fragment() {

    private lateinit var backButton: ImageButton

    private var isBackEnabled: Boolean = true
    private var iconColorID: Int = R.color.background

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isBackEnabled = it.getBoolean(BACK_PARAMETER)
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
        bindViews()
        setupViews()
        bindListeners()
    }

    private fun bindViews() {
        if (view == null) return
        backButton = requireView().findViewById(R.id.backButton)
    }

    private fun setupViews() {
        context?.let {
            val color = ContextCompat.getColor(it, iconColorID)
            ImageViewCompat.setImageTintList(backButton, ColorStateList.valueOf(color))
        }
        backButton.visibility = if (isBackEnabled) View.VISIBLE else View.GONE
    }

    private fun bindListeners() {
        backButton.setOnClickListener(onBackListener)
    }

    private val onBackListener = View.OnClickListener {
        if (activity != null) {
            requireActivity().onBackPressed()
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