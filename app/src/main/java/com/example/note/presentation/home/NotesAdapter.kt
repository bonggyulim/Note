package com.example.note.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.note.databinding.RecyclerviewBinding
import com.example.note.presentation.create.model.NoteModel

class NotesAdapter : ListAdapter<NoteModel, NotesAdapter.Holder>(DiffCallback) {

    interface EditClick { fun editClick(noteModel: NoteModel) }
    interface DeleteClick { fun deleteClick(noteModel: NoteModel) }

    var editClick: EditClick? = null
    var deleteClick: DeleteClick? = null

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel) = oldItem == newItem
        }
    }

    class Holder(private val binding: RecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModel, editClick: EditClick?, deleteClick: DeleteClick?) {
            binding.title.text = item.title
            binding.content.text = item.content

            binding.ivEdit.setOnClickListener { editClick?.editClick(item) }
            binding.ivDelete.setOnClickListener { deleteClick?.deleteClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), editClick, deleteClick)
    }
}