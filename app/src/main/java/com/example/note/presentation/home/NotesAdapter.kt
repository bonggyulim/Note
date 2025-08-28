package com.example.note.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.note.R
import com.example.note.databinding.RecyclerviewBinding
import com.example.note.presentation.create.model.NoteModel

class NotesAdapter : ListAdapter<NoteModel, NotesAdapter.Holder>(DiffCallback) {

    interface EditClick { fun editClick(noteModel: NoteModel) }
    interface DeleteClick { fun deleteClick(noteModel: NoteModel) }
    interface NoteClick { fun noteClick(noteModel: NoteModel)}

    var editClick: EditClick? = null
    var deleteClick: DeleteClick? = null
    var noteClick: NoteClick? = null

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel) = oldItem == newItem
        }
    }

    class Holder(private val binding: RecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModel, editClick: EditClick?, deleteClick: DeleteClick?, noteClick: NoteClick?) {
            binding.title.text = item.title
            binding.content.text = item.content
            binding.summarize.text = item.summarize

            binding.root.setOnClickListener {
                if (binding.content.visibility == View.VISIBLE) {
                    binding.content.visibility = View.INVISIBLE
                    binding.ivEdit.visibility = View.INVISIBLE
                    binding.ivDelete.visibility = View.INVISIBLE
                    binding.summarize.visibility = View.VISIBLE
                } else {
                    binding.content.visibility = View.VISIBLE
                    binding.ivEdit.visibility = View.VISIBLE
                    binding.ivDelete.visibility = View.VISIBLE
                    binding.summarize.visibility = View.INVISIBLE
                }
            }
            binding.ivEdit.setOnClickListener { editClick?.editClick(item) }
            binding.ivDelete.setOnClickListener { deleteClick?.deleteClick(item) }

            val score = item.sentiment
            val colorRes = when (score) {
                0.0 -> R.color.sentiment_neutral
                in 0.8..1.0 -> R.color.sentiment_very_positive
                in 0.6..0.8 -> R.color.sentiment_positive
                in 0.4..0.6 -> R.color.sentiment_neutral
                in 0.2..0.4 -> R.color.sentiment_negative
                in 0.0..0.2 -> R.color.sentiment_very_negative
                else -> R.color.sentiment_neutral
            }
            val bgColor = ContextCompat.getColor(itemView.context, colorRes)
            binding.root.setBackgroundColor(bgColor)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), editClick, deleteClick, noteClick)
    }
}