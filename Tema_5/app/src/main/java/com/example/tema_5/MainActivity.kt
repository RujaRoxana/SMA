package com.example.tema_5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tema_5.data.local.AppDatabase
import com.example.tema_5.data.remote.FirebaseService
import com.example.tema_5.data.repository.NoteRepository
import com.example.tema_5.ui.NoteScreen
import com.example.tema_5.ui.NoteViewModel
import com.example.tema_5.ui.NoteViewModelFactory
import com.example.tema_5.ui.theme.Tema_5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(this)
        val firebase = FirebaseService()
        val repo = NoteRepository(firebase, db.noteDao(), this)

        setContent {

            val vm: NoteViewModel = viewModel(
                factory = NoteViewModelFactory(repo)
            )

            Tema_5Theme {
                NoteScreen(viewModel = vm)
            }
        }
    }
}
