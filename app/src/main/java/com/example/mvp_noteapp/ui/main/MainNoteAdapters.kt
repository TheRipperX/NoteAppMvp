package com.example.mvp_noteapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvp_noteapp.R
import com.example.mvp_noteapp.data.model.NoteEntity
import com.example.mvp_noteapp.databinding.MainLayoutAdapterBinding
import com.example.mvp_noteapp.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainNoteAdapters @Inject constructor(@ApplicationContext context: Context): RecyclerView.Adapter<MainNoteAdapters.MainViewHolder>() {

    private lateinit var binding: MainLayoutAdapterBinding
    private var emptyNote = listOf<NoteEntity>()

    inner class MainViewHolder: RecyclerView.ViewHolder(binding.root) {

        fun dataNote(item: NoteEntity) {
            binding.apply {

                txtTitle.text = item.title
                txtDesc.text = item.desc

                //categorise
                when(item.category) {
                    WORK -> imgCategorise.setImageResource(R.drawable.work)
                    HOME -> imgCategorise.setImageResource(R.drawable.home)
                    HEALTHY -> imgCategorise.setImageResource(R.drawable.healthy)
                    LEARNING -> imgCategorise.setImageResource(R.drawable.learning)
                }

                //priority
                when(item.priority) {
                    HIGH -> cardAdapter.setBackgroundResource(R.drawable.bg_high)
                    NORMAL -> cardAdapter.setBackgroundResource(R.drawable.bg_normal)
                    LOW -> cardAdapter.setBackgroundResource(R.drawable.bg_low)
                }
            }
        }
    }

    private var itemClickListener: ((NoteEntity, String) -> Unit)? = null

    fun setClick(click: (NoteEntity, String) -> Unit) {
        itemClickListener = click
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = MainLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder()
    }

    override fun getItemCount(): Int {
        return emptyNote.count()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.dataNote(emptyNote[position])
        holder.setIsRecyclable(false)
    }

    fun getData(items: List<NoteEntity>) {

        val diffutilsAdapter = DiffutilsAdapter(emptyNote, items)
        val diffUtil = DiffUtil.calculateDiff(diffutilsAdapter)
        emptyNote = items
        diffUtil.dispatchUpdatesTo(this)
    }
}