package com.news.client

import com.news.data.dto.Photos
import com.news.data.dto.PhotosNew
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {


    @GET("/api/photos?limit=10")
    fun getPhotos(@Query("page") page: Int): Single<Photos>
}