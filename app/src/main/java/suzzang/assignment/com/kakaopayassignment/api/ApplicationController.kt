package suzzang.assignment.com.kakaopayassignment.api

import android.app.Application
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application() {
    lateinit var networkService: NetworkService
    private val baseUrl = "https://dapi.kakao.com/"
    companion object {
        lateinit var instance : ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        buildNetwork()
    }

    fun buildNetwork() {
        val builder = Retrofit.Builder()
        var retrofit = builder
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())//rxjava와 retrofit사용을 위해
            .addConverterFactory(GsonConverterFactory.create())//GSON을 JSON으로 쓸수있도록
            .build()

        networkService = retrofit.create(NetworkService::class.java)
    }
}