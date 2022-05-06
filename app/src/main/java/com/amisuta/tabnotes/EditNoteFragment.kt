package com.amisuta.tabnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.amisuta.tabnotes.databinding.FragmentEditNoteBinding
import android.util.Log
import android.view.*
import androidx.fragment.app.activityViewModels
import com.amisuta.tabnotes.viewmodel.NoteViewModel
import com.amisuta.tabnotes.viewmodel.NoteViewModelFactory
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [EditNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditNoteFragment : BaseFragment() {
    private val model: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NotesApplication).repository)
    }
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId", -1)
    }

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

        if (noteId != -1) {

            Log.d("Marc", "pre-fetch")
            model.fetch(noteId)
            model.fetchedNote.observe(viewLifecycleOwner) { note ->
                Log.d("Marc", "change observed!")
                binding.etNoteTitle.setText(note.title)
                binding.etNoteBody.setText(note.body)
            }
            Log.d("Marc", "post-fetch")

        }

        binding.icSave.setOnClickListener {
            if (noteId != -1) {
                updateNote()
            } else {
                saveNote()
            }
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Marc", "Destroyed fragment EditNote")
    }

    private fun saveNote() {
        // if all fields empty, do nothing
        if (binding.etNoteBody.text.isNullOrEmpty()
            && binding.etNoteTitle.text.isNullOrEmpty()
        ) {
            return
        }
        Log.d("Marc", "Executing saveNote()")
        val title = binding.etNoteTitle.text.toString()
        val body = binding.etNoteBody.text.toString()

        model.insert(title, body)

    }

    private fun updateNote() {
        Log.d("Marc", "updating note")
        if (binding.etNoteBody.text.isNullOrEmpty()
            && binding.etNoteTitle.text.isNullOrEmpty()
        ) {
            model.delete(noteId)
        } else {
            val title = binding.etNoteTitle.text.toString()
            val body = binding.etNoteBody.text.toString()
            model.update(title, body)
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
        fun newInstance() = EditNoteFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}