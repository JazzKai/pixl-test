package com.example.pixltest.view_model

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewActivityVM : ViewModel() {
    var fullScreenLoading: MutableLiveData<Int> = MutableLiveData()
    var url: MutableLiveData<String> = MutableLiveData()
    var searchUrl: MutableLiveData<String> = MutableLiveData()
    var urlList = ArrayList<String>()
    var closeKeyboard: MutableLiveData<Boolean> = MutableLiveData()

    fun init() {
        fullScreenLoading.value = View.GONE
    }

    fun search() {
        if (!searchUrl.value.isNullOrBlank()) {
            fullScreenLoading.value = View.VISIBLE
            closeKeyboard.postValue(true)
            url.value = "https://" + searchUrl.value
        }
    }

    private inner class Client : WebViewClient() {
        override fun onReceivedError(
            view: WebView, request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            fullScreenLoading.postValue(View.GONE)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            fullScreenLoading.postValue(View.GONE)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            urlList.add(url.toString())
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let {
                view?.loadUrl(url.toString())
            }
            return true
        }
    }

    fun getWebViewClient(): WebViewClient {
        return Client()
    }
}