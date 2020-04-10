package com.news.data.dto

enum class NewsType {
    News

}

data class News(
    private val id: Int,
    private val name: String? = null,
    private val dateCreate: Int? = null,
    private val description: String? = null,
    private val url: String? = null,
    private val typeNew: Boolean? = null,
    private val typePopular: Boolean? = null,
    private val image: Image? = null,
    private val user: String? = null

) {
    val myUrl = "http://gallery.dev.webant.ru/media/"
    var countOfPages = 0


}