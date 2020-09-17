package com.example.oposiciones.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oposiciones.R
import com.example.oposiciones.data.Block

class BlockListAdapter(
    context: Context,
    listener: ((Block) -> Unit)
) : RecyclerView.Adapter<BlockListAdapter.BlockViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var blocks = emptyList<Block>()
    private val listener: ((Block)->Unit) = listener

    class BlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val blockNameTextView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        val textView = inflater.inflate(R.layout.block_list_item, parent, false)
        return BlockViewHolder(textView)
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        val current = blocks[position]
        holder.blockNameTextView.text = current.description.toUpperCase()
        holder.itemView.setOnClickListener { listener(current) }
    }

    internal fun setBlocks(blocks: List<Block>) {
        this.blocks = blocks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return blocks.size
    }


}