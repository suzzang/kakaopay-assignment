package suzzang.assignment.com.kakaopayassignment.api

import io.reactivex.Observable
import retrofit2.http.*
import suzzang.assignment.com.kakaopayassignment.mvp.model.ImagesResponse


interface NetworkService {

    @GET("/v2/search/image")
    fun getImgList(@Header("Authorization")header:String,
                   @Query("query") keyword: String,
                   @Query("page") page:Int,
                   @Query("sort") sort:String): Observable<ImagesResponse>

}