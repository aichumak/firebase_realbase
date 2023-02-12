package com.example.firebasedatabase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val id: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val lastName: String? = null
)
