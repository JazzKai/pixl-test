package com.example.pixltest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pixltest.R
import com.example.pixltest.databinding.ActivityWebviewBinding
import com.example.pixltest.view_model.WebViewActivityVM

class WebViewActivity : AppCompatActivity() {

    private lateinit var viewModel: WebViewActivityVM
    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WebViewActivityVM::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.activity = this

        viewModel.init()

        initView()
        observer()
    }

    private fun initView() {

    }

    private fun observer() {
        viewModel.closeKeyboard.observe(this, { value ->
            if (value) {
                hideKeyboard()
            }
        })

    }

    fun hideKeyboard() {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun counterBackAction() {
        if (viewModel.urlList.size > 2) {
            if (viewModel.urlList[1] == binding.webView.url || binding.webView.url == "about:blank") {
                finish()
            } else {
                binding.webView.goBack()
            }
        } else {
            finish()

        }

    }

    override fun onBackPressed() {
        counterBackAction()
    }
}