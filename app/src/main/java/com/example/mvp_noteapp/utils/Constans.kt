package com.example.mvp_noteapp.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Delete

//database note table and name
const val NOTE_TABLE = "note_table"
const val NOTE_DATABASE = "note_database"

// database version
const val DATABASE_VERSION = 1

// categorise name item
const val WORK = "work"
const val HOME = "home"
const val LEARNING = "learning"
const val HEALTHY = "healthy"

// priority name item
const val HIGH = "high"
const val NORMAL = "normal"
const val LOW = "low"

//menu adapter items
const val EDIT = "edit"
const val DELETE = "delete"


// differ adapter diffutils
class DiffutilsAdapter(private val old: List<*>, private val new: List<*>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }

}