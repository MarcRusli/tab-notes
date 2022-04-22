package com.amisuta.tabnotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amisuta.tabnotes.R
import com.amisuta.tabnotes.entities.Notes

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var arrNotesList = ArrayList<Notes>()

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.rv_item_title)
        val textViewBody: TextView = view.findViewById(R.id.rv_item_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrNotesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.textViewTitle.text = arrNotesList[position].title
        holder.textViewBody.text = arrNotesList[position].body

    }


    fun getData(updatedList: List<Notes>) {
      arrNotesList.clear()
      arrNotesList.addAll(updatedList)
      notifyDataSetChanged()
    }
    //bug: never saves 1st 2 notes created upon launching app,
    //but saves every other note past that


    /*
    fun getData(notesList: List<Notes>) {
        arrNotesList = notesList as ArrayList<Notes>
    }
     */

}