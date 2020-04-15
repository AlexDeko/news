package com.news.client

import com.news.data.dto.News
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoApi {

    @GET("/api/photos/{id}")
    fun getPosts(@Path("id") id: String): Single<News>
}