package com.news.data.dto

import com.google.gson.annotations.SerializedName

class Image(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String) {
}