package com.example.mvp_noteapp.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.mvp_noteapp.data.model.NoteEntity
import com.example.mvp_noteapp.data.repository.add.AddNoteRepository
import com.example.mvp_noteapp.databinding.FragmentNoteBinding
import com.example.mvp_noteapp.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment(), NoteContracts.View {

    private val TAG = "TAG"

    private lateinit var binding: FragmentNoteBinding

    //inject
    @Inject
    lateinit var addNoteRepository: AddNoteRepository
    @Inject
    lateinit var noteEntity: NoteEntity

    //note presenter
    @Inject
    lateinit var notePresenter: NotePresenter

    //note presenter
    //private val presenter: NotePresenter by lazy { NotePresenter(addNoteRepository, this) }

    // category items
    private lateinit var categoryList: MutableList<String>
    private var category = ""

    //priority items
    private lateinit var priorityList: MutableList<String>
    private var priority = ""

    // edit database method
    private var noteId = 0
    private var type = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main()
    }

    private fun main() {

        //note id to arguments
        noteId = arguments?.getInt(BUNDLE_ID) ?: 0

        // if edit or add note
        type = if (noteId > 0) EDIT else NEW

        if (type == EDIT){
            notePresenter.getNote(noteId)
        }

        binding.apply {

            imgClose.setOnClickListener { this@NoteFragment.dismiss() }

            spinnerCategoriseFun()
            spinnerPriorityFun()

            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val dec = edtDec.text.toString()

                if (title.isEmpty() || dec.isEmpty() || category.isEmpty() || priority.isEmpty()){
                    Toast.makeText(requireContext(), "please enter valid items...", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // set data note
                //noteEntity.id = 0
                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.desc = dec
                noteEntity.category = category
                noteEntity.priority = priority

                //save and edit note database

                if (type == EDIT)
                    notePresenter.updateNote(noteEntity)
                else
                    notePresenter.saveNoteApp(noteEntity)
            }

        }
    }

    private fun spinnerCategoriseFun() {
        categoryList = mutableListOf(WORK, HOME, HEALTHY, LEARNING)
        binding.apply {

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategorise.adapter = adapter
            spinnerCategorise.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    category = categoryList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

        }

    }

    private fun spinnerPriorityFun() {
        priorityList = mutableListOf(HIGH, NORMAL, LOW)
        binding.apply {

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPriority.adapter = adapter
            spinnerPriority.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    priority = priorityList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }

        }

    }

    private fun getSpinnerItems(list: MutableList<String>, item: String): Int {
        var index = 0
        for (i in list.indices){
            if (list[i] == item){
                index = i
                break
            }
        }
        return index
    }

    override fun close() {
        this.dismiss()
    }

    override fun loadNote(n: NoteEntity) {
        if (this.isAdded){
            requireActivity().runOnUiThread {
                binding.apply {

                    edtTitle.setText(n.title)
                    edtDec.setText(n.desc)
                    spinnerCategorise.setSelection(getSpinnerItems(categoryList, n.category))
                    spinnerPriority.setSelection(getSpinnerItems(priorityList, n.priority))

                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        notePresenter.onStopApp()
    }
}