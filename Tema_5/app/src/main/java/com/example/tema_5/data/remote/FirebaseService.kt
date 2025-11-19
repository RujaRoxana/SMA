package com.example.tema_5.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseService {

    private val db = Firebase.firestore

    suspend fun getNotes(): List<String> {
        val snapshot = db.collection("notes").get().await()
        return snapshot.documents.map { it.getString("text") ?: "" }
    }

    suspend fun addNote(text: String) {
        val data = mapOf("text" to text)
        db.collection("notes").add(data).await()
    }
}
