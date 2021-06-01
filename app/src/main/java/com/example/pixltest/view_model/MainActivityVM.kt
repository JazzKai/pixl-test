package com.example.pixltest.view_model

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import com.example.pixltest.adapter.ImagesRVAdapter
import com.example.pixltest.helper.Interface
import com.example.pixltest.helper.RestApi
import com.example.pixltest.model.SearchImageResponse
import com.example.pixltest.room.AppDatabase
import com.example.pixltest.room.SearchValueDataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import java.util.concurrent.Executors

class MainActivityVM : ViewModel() {

    var pageNumber: MutableLiveData<Int> = MutableLiveData()
    var q: MutableLiveData<String> = MutableLiveData()
    var fullScreenLoading: MutableLiveData<Int> = MutableLiveData()
    var showLoadingMore: MutableLiveData<Int> = MutableLiveData()
    lateinit var imagesRVAdapter: ImagesRVAdapter
    var toastText: MutableLiveData<String> = MutableLiveData()
    var currentLastItemPos: MutableLiveData<Int> = MutableLiveData()
    var closeKeyboard: MutableLiveData<Boolean> = MutableLiveData()
    var showImageValue: MutableLiveData<SearchImageResponse.Value> = MutableLiveData()
    lateinit var db: RoomDatabase

    fun init() {
        fullScreenLoading.value = View.GONE
        showLoadingMore.value = View.GONE
        pageNumber.value = 1
        imagesRVAdapter = ImagesRVAdapter(this)

    }

    fun searchImage() {
        q.value?.let {
            imagesRVAdapter.removeAllData()
            closeKeyboard.postValue(true)
            pageNumber.value = 1
            retrieveFromDB(
                q.value.toString(),
                pageNumber.value.toString()
            )
        }
    }

    fun callApi() {
        RestApi.searchImage(
            q.value.toString(),
            pageNumber.value.toString(),
            "100",
            "true",
            object : Interface.apiCompletionSearchImage {
                override fun onSuccess(searchImageResponse: SearchImageResponse?) {
                    hideLoading()
                    searchImageResponse?.value?.let {
                        if (it.isNotEmpty()) {
                            imagesRVAdapter.setImageValues(it)
                            storeToDB(
                                q.value.toString().toLowerCase(Locale.getDefault()).trim(),
                                pageNumber.value.toString(),
                                it
                            )
                        } else {
                            toastText.postValue("No results found.")
                        }
                    }
                }

                override fun onFailure(e: String?) {
                    hideLoading()
                    toastText.postValue(e)
                }
            })

    }

    fun loadMore(lastPos: Int) {
        if (lastPos == (imagesRVAdapter.itemCount - 1) && (showLoadingMore.value != View.VISIBLE)) {
            pageNumber.value = pageNumber.value!!.plus(1)
            retrieveFromDB(q.value.toString(), pageNumber.value.toString())
        }
    }

    fun showImageDialog(value: SearchImageResponse.Value) {
        showImageValue.value = value
    }

    fun hideLoading() {
        fullScreenLoading.postValue(View.GONE)
        showLoadingMore.postValue(View.GONE)
    }

    fun storeToDB(
        searchValue: String,
        pageNumber: String,
        values: List<SearchImageResponse.Value>
    ) {
        Log.e("pageNumber", pageNumber)
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val searchValueDataEntity =
                SearchValueDataEntity("$searchValue+$pageNumber", Gson().toJson(values))
            (db as AppDatabase).searchValueDao().insertAll(searchValueDataEntity)
        }

    }

    fun retrieveFromDB(
        searchValue: String,
        pageNumber: String
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val id = "${searchValue.toLowerCase(Locale.getDefault()).trim()}+$pageNumber"
            val values: List<SearchValueDataEntity> =
                (db as AppDatabase).searchValueDao().loadAllByIds(id)
            handler.post {
                if (values.isNotEmpty()) {
                    imagesRVAdapter.setImageValues(jsonToModel(values[0].value.toString()))
                } else {
                    if (pageNumber == "1") {
                        fullScreenLoading.value = View.VISIBLE
                    } else {
                        showLoadingMore.value = View.VISIBLE
                    }
                    callApi()
                }
            }
        }

    }

    fun jsonToModel(value: String): List<SearchImageResponse.Value> {
        val itemType = object : TypeToken<List<SearchImageResponse.Value>>() {}.type
        return Gson().fromJson(value, itemType)
    }
}