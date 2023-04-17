package com.example.mvp_noteapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvp_noteapp.databinding.ActivityMainBinding
import com.example.mvp_noteapp.ui.add.NoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        main()
    }

    private fun main() {

        binding.apply {

            //floating btn click
            floatingBtn.setOnClickListener { NoteFragment().show(supportFragmentManager, NoteFragment().tag) }
        }

    }
}