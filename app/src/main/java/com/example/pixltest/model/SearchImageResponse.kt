package com.example.pixltest.model

import com.google.gson.annotations.SerializedName

class SearchImageResponse {
    @SerializedName("type")
    val type: String? = null

    @SerializedName("totalCount")
    val totalCount: Int? = null

    @SerializedName("value")
    val value: List<Value>? = null

    inner class Value {
        @SerializedName("url")
        val url: String? = null

        @SerializedName("height")
        val height: Long? = null

        @SerializedName("width")
        val width: Int? = null

        @SerializedName("thumbnail")
        val thumbnail: String? = null

        @SerializedName("thumbnailHeight")
        val thumbnailHeight: Int? = null

        @SerializedName("thumbnailWidth")
        val thumbnailWidth: Int? = null

        @SerializedName("base64Encoding")
        val base64Encoding: Any? = null

        @SerializedName("name")
        val name: String? = null

        @SerializedName("title")
        val title: String? = null

        @SerializedName("provider")
        val provider: Provider? = null

        @SerializedName("imageWebSearchURL")
        val imageWebSearchURL: String? = null

        @SerializedName("webpageURL")
        val webpageURL: String? = null

        inner class Provider {
            @SerializedName("name")
            val name: String? = null

            @SerializedName("favIcon")
            val favIcon: String? = null

            @SerializedName("favIconBase64Encoding")
            val favIconBase64Encoding: String? = null
        }
    }

}