package com.example.amphsesviewer.domain.model

data class Album(
    val id: Long,
    val name: String,
    val ImagesId: List<Long>
)