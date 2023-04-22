package com.example.mvp_noteapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvp_noteapp.data.model.NoteEntity
import com.example.mvp_noteapp.data.repository.main.MainRepository
import com.example.mvp_noteapp.databinding.ActivityMainBinding
import com.example.mvp_noteapp.ui.add.NoteFragment
import com.example.mvp_noteapp.utils.BUNDLE_ID
import com.example.mvp_noteapp.utils.DELETE
import com.example.mvp_noteapp.utils.EDIT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContracts.View {

    private lateinit var binding: ActivityMainBinding

    //repository
    @Inject
    lateinit var mainRepository: MainRepository

    //adapters
    @Inject
    lateinit var mainNoteAdapters: MainNoteAdapters

    //note fragment
    @Inject
    lateinit var mainPresenter: MainPresenter

    //presenter
    //private val mainPresenter:MainPresenter by lazy { MainPresenter(mainRepository, this) }

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

        // show all data database
        mainPresenter.showAllNotes()

        //menu popup click
        mainNoteAdapters.setClick { noteEntity, s ->

            when(s) {

                EDIT -> {

                    val fragment = NoteFragment()
                    val bundle = Bundle()
                    bundle.putInt(BUNDLE_ID, noteEntity.id)
                    fragment.arguments = bundle
                    fragment.show(supportFragmentManager, fragment.tag)
                }

                DELETE -> {
                    mainPresenter.deleteNote(noteEntity)
                }
            }

        }

    }

    override fun onStop() {
        super.onStop()
        mainPresenter.onStopApp()
    }

    override fun showData(notes: List<NoteEntity>) {

        binding.apply {

            emptyLayout.visibility = View.GONE
            listMain.visibility = View.VISIBLE

            //adapter data
            mainNoteAdapters.getData(notes)

            //list show
            listMain.apply {
                layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL)
                adapter = mainNoteAdapters
            }
        }
    }

    override fun emptyList() {
        binding.apply {
            emptyLayout.visibility = View.VISIBLE
            listMain.visibility = View.GONE
        }
    }

    override fun deleteNoteSuccess() {
        Snackbar.make(binding.root, "delete note success", Snackbar.LENGTH_SHORT).show()
    }
}