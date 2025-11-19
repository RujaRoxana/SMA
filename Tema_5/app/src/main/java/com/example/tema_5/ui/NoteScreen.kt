package com.example.tema_5.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(viewModel: NoteViewModel) {

    val notes = viewModel.notes
    val isLoading = viewModel.isLoading

    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tema 5 – Backup Notes") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Note") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        viewModel.addNote(text)
                        text = ""
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Adaugă")
            }

            if (isLoading) {
                Text("Se încarcă...", modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(Modifier.height(16.dp))

            Text("Note salvate:", style = MaterialTheme.typography.titleLarge)

            LazyColumn {
                items(notes) { note ->
                    Text("• $note", modifier = Modifier.padding(6.dp))
                }
            }
        }
    }
}
