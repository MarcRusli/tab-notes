package com.amisuta.tabnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amisuta.tabnotes.databinding.ActivityMainBinding
import com.amisuta.tabnotes.viewmodel.NoteViewModel
import com.amisuta.tabnotes.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment, ifTransition: Boolean = true) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

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
}