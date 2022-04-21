package com.amisuta.tabnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amisuta.tabnotes.adapter.NotesAdapter
import com.amisuta.tabnotes.database.NotesDatabase
import com.amisuta.tabnotes.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


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

        launch {
            context?.let {
                val notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                binding.recyclerView.adapter = NotesAdapter(notes)
            }
        }

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