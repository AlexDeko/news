package com.news.data.dto

import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("totalItems")
    var totalItems: Int = 0,
    @SerializedName("itemsPerPage")
    val itemsPerPage: Int = 30,
    @SerializedName("countOfPages")
    var countOfPages: Int = 0,
    @SerializedName("data")
    val news: List<News>?
) {

}

