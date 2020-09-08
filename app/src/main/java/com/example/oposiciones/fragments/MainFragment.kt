package com.example.oposiciones.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oposiciones.R
import com.example.oposiciones.activities.BLOCK_ID
import com.example.oposiciones.activities.LessonsActivity
import com.example.oposiciones.adapters.BlockListAdapter
import com.example.oposiciones.data.Block
import com.example.oposiciones.viewmodels.BlockViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: BlockViewModel
    private lateinit var blockRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlockViewModel::class.java)
        bindViews()
        setupRecyclerView()
    }

    private fun bindViews() {
        blockRecyclerView = requireView().findViewById(R.id.recyclerView)
    }

    private fun setupRecyclerView() {
        context?.let { context ->
            val layoutManager = LinearLayoutManager(context)
            val adapter = BlockListAdapter(context, onSelectBlockListener)
            blockRecyclerView.apply {
                this.adapter = adapter
                this.layoutManager = layoutManager
            }
            viewModel.allBlocks.observe(viewLifecycleOwner, Observer { blocks ->
                adapter.setBlocks(blocks)
            })
        }

    }

    private val onSelectBlockListener: (Block) -> Unit = { block ->
        val intent = Intent(context, LessonsActivity::class.java).apply {
            putExtra(BLOCK_ID, block.id)
        }
        startActivity(intent)
    }

}