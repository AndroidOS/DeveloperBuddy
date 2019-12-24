package com.example.developerbuddy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stack
    (
    val user_id: Int?,
    val reputation: Int?,
    val user_type: String?,
    val profile_image: String?,
    val display_name: String?,
    val name: String?,
    val accept_rate: Int?,
    val badge_type: String?,
    val badge_id: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}