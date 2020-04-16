package com.news.data.dto

data class PhotosNew(
    val countOfPages: Int,
    val `data`: List<Data>,
    val itemsPerPage: Int,
    val totalItems: Int
)