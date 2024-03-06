package com.dicoding.asclepius.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var label: String,
    var confidence: String,
    var imageBase64: String,
)
