package com.news.client

import com.news.data.dto.Photos
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularPhotosApi {

    @GET("/api/photos?popular=true&limit=10")
    fun getPhotos(@Query("page") page: Int): Single<Photos>
}