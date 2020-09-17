package com.example.oposiciones.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.oposiciones.R
import com.example.oposiciones.activities.BLOCK_ID
import com.example.oposiciones.activities.LessonsActivity
import com.example.oposiciones.adapters.BlockListAdapter
import com.example.oposiciones.data.Block
import com.example.oposiciones.datamanager.utils.Status
import com.example.oposiciones.viewmodels.BlockViewModel


class BlockFragment : Fragment() {

    companion object {
        fun newInstance() = BlockFragment()
    }

    private lateinit var viewModel: BlockViewModel
    private lateinit var blockRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlockViewModel::class.java)
        bindViews()
        setupRecyclerView()
        fetchBlocks()
    }

    private fun bindViews() {
        blockRecyclerView = requireView().findViewById(R.id.recyclerView)
        swipeRefreshLayout = requireView().findViewById(R.id.swipeRefreshLayout)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = BlockListAdapter(requireContext(), onSelectBlockListener)
        blockRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
        viewModel.blocks.observe(viewLifecycleOwner, Observer { blocks ->
            adapter.setBlocks(blocks)
        })
        swipeRefreshLayout.setOnRefreshListener {
            fetchBlocks()
        }
    }

    private val onSelectBlockListener: (Block) -> Unit = { block ->
        val intent = Intent(context, LessonsActivity::class.java).apply {
            putExtra(BLOCK_ID, block.id)
        }
        startActivity(intent)
    }

    private fun fetchBlocks() {
        viewModel.fetchBlocks().observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            when (it) {
                Status.ERROR -> {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}