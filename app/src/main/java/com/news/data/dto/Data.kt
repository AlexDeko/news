package com.news.data.dto

data class Data(
    val dateCreate: String,
    val description: String,
    val id: Int,
    val image: ImageX,
    val name: String,
    val new: Boolean,
    val popular: Boolean,
    val user: String
)