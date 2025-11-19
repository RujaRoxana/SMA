package com.example.tema_5.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.tema_5.data.local.NoteDao
import com.example.tema_5.data.local.NoteEntity
import com.example.tema_5.data.remote.FirebaseService

class NoteRepository(
    private val firebase: FirebaseService,
    private val dao: NoteDao,
    private val context: Context
) {

    private fun hasInternet(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    suspend fun getNotes(): List<String> {
        return if (hasInternet()) {
            val notes = firebase.getNotes()
            dao.clear()
            notes.forEach { dao.insert(NoteEntity(text = it)) }
            notes
        } else {
            dao.getAll().map { it.text }
        }
    }

    suspend fun addNote(text: String) {
        if (hasInternet()) {
            firebase.addNote(text)
        } else {
            dao.insert(NoteEntity(text = text))
        }
    }

    suspend fun syncLocalToCloud() {
        if (hasInternet()) {
            val localNotes = dao.getAll()
            localNotes.forEach { firebase.addNote(it.text) }
            dao.clear()
        }
    }
}
