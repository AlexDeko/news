package com.news

import android.app.Application
import com.news.client.PhotoApi
import com.news.client.PhotosApi
import com.news.client.PopularPhotosApi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                networkModule
            )
        }
    }
}

val networkModule = module {

    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("http://gallery.dev.webant.ru")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single { get<Retrofit>().create(PhotoApi::class.java) }
    single { get<Retrofit>().create(PhotosApi::class.java) }
    single { get<Retrofit>().create(PopularPhotosApi::class.java) }
}