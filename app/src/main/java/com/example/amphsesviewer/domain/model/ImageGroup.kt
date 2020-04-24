package com.example.amphsesviewer.domain.model

data class ImageGroup(
    val id: Long = 0,
    val name: String = "",
    val idList: MutableList<Long> = ArrayList()
)

