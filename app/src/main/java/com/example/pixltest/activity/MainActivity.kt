package com.example.pixltest.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.pixltest.R
import com.example.pixltest.databinding.ActivityMainBinding
import com.example.pixltest.helper.Utilities
import com.example.pixltest.room.AppDatabase
import com.example.pixltest.view_model.MainActivityVM

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityVM
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityVM::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.activity = this

        viewModel.init()

        initView()
        observer()
    }

    private fun initView() {
        viewModel.db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-image"
        ).build()
    }

    private fun observer() {
        viewModel.toastText.observe(this, { content ->
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
        })

        viewModel.closeKeyboard.observe(this, { value ->
            if (value) {
                hideKeyboard()
            }
        })

        viewModel.currentLastItemPos.observe(this, {
            if (it != -1) {
                viewModel.loadMore(it)
            }
        })

        viewModel.showImageValue.observe(this, { value ->
            if (value != null) {
                val dialog = Utilities.showImageDialog(this, value)

                dialog.setOnDismissListener {
                    viewModel.showImageValue.value = null
                }
            }
        })

    }

    fun toWevView() {
        var intent = Intent(this, WebViewActivity::class.java)
        startActivity(intent)
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}