package com.amisuta.tabnotes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amisuta.tabnotes.databinding.FragmentEditNoteBinding
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.amisuta.tabnotes.NotesApplication
import com.amisuta.tabnotes.viewmodel.NoteViewModel
import com.amisuta.tabnotes.viewmodel.NoteViewModelFactory

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

        // launching background thread for database access
        model.insert(title, body)

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