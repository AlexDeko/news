package com.news.data.dto

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("dateCreate")
    val dateCreate: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("new")
    val typeNew: Boolean? = null,
    @SerializedName("popular")
    val typePopular: Boolean? = null,
    @SerializedName("image")
    val image: Image? = null,
    @SerializedName("user")
    val user: String? = null

) {
}