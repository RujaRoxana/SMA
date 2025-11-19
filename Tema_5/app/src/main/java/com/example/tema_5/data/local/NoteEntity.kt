package com.example.tema_5.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity
data class NoteEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val text: String
)
