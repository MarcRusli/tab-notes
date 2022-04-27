package com.amisuta.tabnotes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amisuta.tabnotes.adapter.NoteListAdapter
import com.amisuta.tabnotes.databinding.FragmentHomeBinding
import com.amisuta.tabnotes.viewmodel.NoteViewModel
import com.amisuta.tabnotes.viewmodel.NoteViewModelFactory

class HomeFragment : BaseFragment() {
    private val model: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NotesApplication).repository)
    }
    private var adapter = NoteListAdapter()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.allNotes.observe(this) { notes ->
            // Update the cached copy of the notes in the adapter.
            notes.let { adapter.getData(it) }
            Log.d("Marc", "observed: $notes")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter

        binding.createNoteBtn.setOnClickListener {
            replaceFragment(EditNoteFragment.newInstance())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment, ifTransition: Boolean = true) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

        if (ifTransition) {
            fragmentTransaction.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }

        fragmentTransaction
            .replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}