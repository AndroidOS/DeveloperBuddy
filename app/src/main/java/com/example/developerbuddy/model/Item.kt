package com.example.developerbuddy.model

data class User
    (
    val reputation: Int?,
    val user_id: Int?,
    val user_type: String?,
    val profile_image: String?,
    val display_name: String?,
    val link: String?,
    val accept_rate: Int?
)

data class Item
    (
    val user: User?,
    val badge_type: String?,
    val rank: String?,
    val badge_id: Int?,
    val link: String?,
    val name: String?
)


data class RootObject
    (
    val items: List<Item>?,
    val has_more: Boolean?,
    val quota_max: Int?,
    val quota_remaining: Int?
)