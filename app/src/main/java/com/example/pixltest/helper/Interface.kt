package com.example.pixltest.helper

import com.example.pixltest.model.SearchImageResponse
import retrofit2.Call
import retrofit2.http.*

object Interface {

    interface apiCompletionSearchImage {
        fun onSuccess(searchImageResponse: SearchImageResponse?)
        fun onFailure(e: String?)
    }

    interface restApi {
        @GET("api/Search/ImageSearchAPI")
        fun imageSearch(
            @Header("x-rapidapi-key") key: String,
            @Query("q") q: String,
            @Query("pageNumber") pageNumber: String,
            @Query("pageSize") pageSize: String,
            @Query("autoCorrect") autoCorrect: String,
        ): Call<SearchImageResponse>
    }
}