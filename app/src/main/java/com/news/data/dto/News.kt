package com.news.data.dto

data class News(

    val id: Int,
    val name: String? = null,
    private val dateCreate: String? = null,
    val description: String? = null,
    val typeNew: Boolean? = null,
    val typePopular: Boolean? = null,
    val image: Image? = null,
    private val user: String? = null

) {
}