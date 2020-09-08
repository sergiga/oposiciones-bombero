package com.example.oposiciones.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.oposiciones.R
import com.example.oposiciones.data.Difficulty
import com.google.android.material.card.MaterialCardView

class DifficultyListAdapter(
    private val context: Context,
    private val listener: ((Difficulty) -> Unit)
) : RecyclerView.Adapter<DifficultyListAdapter.DifficultyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var difficulties = emptyList<Difficulty>()

    class DifficultyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val difficultyTextView: TextView = itemView.findViewById(R.id.textView)
        val cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DifficultyViewHolder {
        val itemView = inflater.inflate(R.layout.difficulty_list_item, parent, false)
        return DifficultyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DifficultyViewHolder, position: Int) {
        val difficulty = difficulties[position]
        holder.difficultyTextView.text = difficulty.description
        if (difficulty.selected) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange))
            holder.difficultyTextView.setTextColor(ContextCompat.getColor(context, R.color.background))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background))
            holder.difficultyTextView.setTextColor(ContextCompat.getColor(context, R.color.text))
        }
        holder.itemView.setOnClickListener { listener(difficulty) }
    }

    internal fun setDifficulties(difficulties: List<Difficulty>) {
        this.difficulties = difficulties
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return difficulties.size
    }


}