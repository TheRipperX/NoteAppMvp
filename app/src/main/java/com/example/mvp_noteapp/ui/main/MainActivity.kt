package com.example.mvp_noteapp.ui.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvp_noteapp.R
import com.example.mvp_noteapp.data.model.NoteEntity
import com.example.mvp_noteapp.data.repository.main.MainRepository
import com.example.mvp_noteapp.databinding.ActivityMainBinding
import com.example.mvp_noteapp.ui.add.NoteFragment
import com.example.mvp_noteapp.utils.*
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

    // filter note priority items
    private var priorityItem = 0

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

            //set toolbar to android
            setSupportActionBar(toolbarMain)

            //floating btn click
            floatingBtn.setOnClickListener { NoteFragment().show(supportFragmentManager, NoteFragment().tag) }

            toolbarMain.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_toolbar_filter -> {
                        filterNoteAlert()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {return@setOnMenuItemClickListener  false}
                }
            }
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
                    priorityItem = 0
                }
                DELETE -> {
                    mainPresenter.deleteNote(noteEntity)
                }
            }
        }
    }

    //menu item android to search toolbar menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchMenu = menu.findItem(R.id.menu_toolbar_search)

        val searchView = searchMenu.actionView as SearchView
        searchView.queryHint = "Search..."

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    mainPresenter.searchNote(it)
                }

                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.onStopApp()
    }

    override fun showData(notes: List<NoteEntity>) {
        priorityItem = 0
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
        priorityItem = 0
        Snackbar.make(binding.root, "delete note success", Snackbar.LENGTH_SHORT).show()
    }

    //filter note alert dialog fun
    private fun filterNoteAlert() {

        val build = AlertDialog.Builder(this)

        val itemDialog = arrayOf(ALL, HIGH, NORMAL, LOW)

        build.setSingleChoiceItems(itemDialog, priorityItem) {d, i ->

            when(i){

                0 -> {mainPresenter.showAllNotes()}

                in 1..itemDialog.size -> {mainPresenter.filterNote(itemDialog[i])}
            }
            priorityItem = i
            d.dismiss()
        }

        build.create()
        build.show()
    }
}