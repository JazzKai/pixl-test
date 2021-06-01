package com.example.pixltest.helper

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


object BindingAdapter {

//    @BindingAdapter("selectedFragment", "activity")
//    @JvmStatic
//    fun changeFragment(frameLayout: FrameLayout, selectedFragment: MutableLiveData<Fragment>, activity: MainActivity) {
//        val manager = activity.supportFragmentManager
//        val transaction = manager.beginTransaction()
//        selectedFragment.value?.let { transaction.replace(frameLayout.id, it) }
//        transaction.commit()
//    }

    @BindingAdapter("selectionPosition")
    @JvmStatic
    fun setSelectionPosition(recyclerView: RecyclerView, selectPosition: Int) {

    }

    @InverseBindingAdapter(attribute = "selectionPosition")
    @JvmStatic
    fun getSelectionPosition(recyclerView: RecyclerView): Int {

        return (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

    }

    @BindingAdapter("selectionPositionAttrChanged")
    @JvmStatic
    fun setRecycleViewOnScrollListener(
        recyclerView: RecyclerView,
        listener: InverseBindingListener
    ) {

        var rvListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                listener.onChange()
            }
        }

        recyclerView.addOnScrollListener(rvListener)
    }


    @BindingAdapter("editTextValue")
    @JvmStatic
    fun setEditTextValue(editText: EditText, query: String?) {
    }

    @InverseBindingAdapter(attribute = "editTextValue")
    @JvmStatic
    fun getEditTextValue(editText: EditText): String {

        return editText.text.toString()

    }

    @BindingAdapter("editTextValueAttrChanged")
    @JvmStatic
    fun setEditTextTextWatcher(editText: EditText, listener: InverseBindingListener) {

        var editTextWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                listener.onChange()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }

        editText.addTextChangedListener(editTextWatcher)
    }

    @BindingAdapter("setGridRVAdapter", "spamCount")
    @JvmStatic
    fun setGridRVAdapter(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        spamCount: Int
    ) {
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, spamCount)
        recyclerView.adapter = adapter
    }

    @BindingAdapter("setImageURL")
    @JvmStatic
    fun setImageURL(imageView: ImageView, url: String) {
        Utilities.glideSetImage(imageView, url)
    }

    @BindingAdapter("setWebViewClient")
    @JvmStatic
    fun setWebViewClient(view: WebView, client: WebViewClient) {
        view.settings.javaScriptEnabled = true
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        view.settings.domStorageEnabled = true
        view.webViewClient = client
    }

    @BindingAdapter("loadUrl")
    @JvmStatic
    fun loadUrl(view: WebView, url: String) {
        view.loadUrl(url)
    }

}