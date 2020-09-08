package com.example.oposiciones.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.oposiciones.R
import com.example.oposiciones.data.Answer
import com.example.oposiciones.data.QuestionWithAnswers
import com.google.android.material.card.MaterialCardView

class QuestionListAdapter(
    private val context: Context,
    private val listener: ((Answer) -> Unit)
) : RecyclerView.Adapter<QuestionListAdapter.AnswerViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var question: QuestionWithAnswers? = null

    class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionTextView: TextView = itemView.findViewById(R.id.textView)
        val cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val itemView = inflater.inflate(R.layout.answer_list_item, parent, false)
        return AnswerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer = question!!.answers[position]
        holder.descriptionTextView.text = answer.description
        holder.itemView.setOnClickListener { listener(answer) }
        if (question!!.question.selectedAnswer == null) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background))
            holder.descriptionTextView.setTextColor(ContextCompat.getColor(context, R.color.text))
            return
        }
        val selectedAnswer = question!!.question.selectedAnswer
        val correctAnswer = question!!.question.answer

        if (answer.letter == correctAnswer) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
            holder.descriptionTextView.setTextColor(ContextCompat.getColor(context, R.color.background))
        } else if (selectedAnswer == answer.letter && selectedAnswer != correctAnswer) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange))
            holder.descriptionTextView.setTextColor(ContextCompat.getColor(context, R.color.background))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background))
            holder.descriptionTextView.setTextColor(ContextCompat.getColor(context, R.color.text))
        }
    }

    internal fun setQuestion(question: QuestionWithAnswers) {
        this.question = question
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return question?.answers?.size ?: 0
    }


}