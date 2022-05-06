package com.amisuta.tabnotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amisuta.tabnotes.R
import com.amisuta.tabnotes.entities.Note

class NoteListAdapter() : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NOTES_COMPARATOR) {
    var listener:OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.body, current.id, listener)
    }

    fun setOnClickListener(lis: OnItemClickListener) {
        listener = lis
    }


    interface OnItemClickListener{
        fun onClicked(noteId:Int)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.rv_item_title)
        private val bodyView: TextView = itemView.findViewById(R.id.rv_item_body)

        fun bind(title: String?, body: String?, id: Int?, clickListener: OnItemClickListener?) {
            titleView.text = title
            bodyView.text = body

            itemView.setOnClickListener {
                clickListener?.onClicked(id!!)
            }
        }

        companion object {
            fun create(parent: ViewGroup): NoteViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_item, parent, false)
                return NoteViewHolder(view)
            }
        }
    }

    companion object {
        private val NOTES_COMPARATOR = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return (oldItem.title == newItem.title) && (oldItem.body == newItem.body)
            }
        }
    }
}