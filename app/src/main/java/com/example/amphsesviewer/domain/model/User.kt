package com.example.amphsesviewer.domain.model

import java.io.Serializable

data class User(
    val id: String = "",
    val userName: String = "",
    val email: String = "",
    val friendsId: List<String> = emptyList(),
    val profileImageUrl: String = ""
): Serializable