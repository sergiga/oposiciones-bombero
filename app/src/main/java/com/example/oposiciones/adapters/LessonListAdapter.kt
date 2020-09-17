package com.example.oposiciones.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oposiciones.R
import com.example.oposiciones.data.Lesson

class LessonListAdapter(
    context: Context,
    listener: ((Lesson) -> Unit)
) : RecyclerView.Adapter<LessonListAdapter.LessonViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var lessons = emptyList<Lesson>()
    private val listener: ((Lesson)->Unit) = listener

    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonNameTextView: TextView = itemView.findViewById(R.id.textView)
        val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val textView = inflater.inflate(R.layout.lesson_list_item, parent, false)
        return LessonViewHolder(textView)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val current = lessons[position]
        holder.lessonNameTextView.text = current.description.toUpperCase()
        if (current.globalScore == null) {
            holder.scoreTextView.visibility = View.GONE
        } else {
            holder.scoreTextView.text = "%.2f".format(current.globalScore)
            holder.scoreTextView.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener { listener(current) }
    }

    internal fun setLessons(lessons: List<Lesson>) {
        this.lessons = lessons
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lessons.size
    }


}