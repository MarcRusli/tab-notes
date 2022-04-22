package com.amisuta.tabnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amisuta.tabnotes.database.NotesDatabase
import com.amisuta.tabnotes.databinding.FragmentEditNoteBinding
import com.amisuta.tabnotes.entities.Notes
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [EditNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditNoteFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private var _binding: FragmentEditNoteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icSave.setOnClickListener {
            saveNote()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveNote() {
        // if all fields empty, do nothing
        if (binding.etNoteBody.text.isNullOrEmpty()
            && binding.etNoteTitle.text.isNullOrEmpty()) {
            return
        }

        // launching background thread for database access
        launch {
            val notes = Notes()
            notes.title = binding.etNoteTitle.text.toString()
            notes.body = binding.etNoteBody.text.toString()

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EditNoteFragment.
         */
        @JvmStatic
        fun newInstance() = EditNoteFragment()

    }
}