package com.example.pixltest.helper

import com.example.pixltest.model.SearchImageResponse
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestApi {
    val key = "b73ce6d795msh2e7fafcd143925dp1f762cjsn172ccbabb3c9";

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .build()

    private val api = Retrofit.Builder()
        .baseUrl("https://contextualwebsearch-websearch-v1.p.rapidapi.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun createAPIProvider(): Interface.restApi {
        return api.create(Interface.restApi::class.java)
    }

    fun searchImage(
        q: String,
        pageNumber: String,
        pageSize: String,
        autoCorrect: String,
        completion: Interface.apiCompletionSearchImage
    ) {
        createAPIProvider().imageSearch(key, q, pageNumber, pageSize, autoCorrect).enqueue(object : Callback<SearchImageResponse> {
            override fun onResponse(
                call: Call<SearchImageResponse>,
                response: Response<SearchImageResponse>
            ) {
                if (response.code() == 200) {
                    completion.onSuccess(response.body())
                } else {
                    completion.onFailure("Failed to retrieve data, please try again.")
                }
            }

            override fun onFailure(call: Call<SearchImageResponse>, t: Throwable) {
                completion.onFailure(t.message)
            }
        })
    }


}