package com.example.pixltest.helper

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.pixltest.R
import com.example.pixltest.model.SearchImageResponse

object Utilities {

    fun showImageDialog(context: Context, value: SearchImageResponse.Value): Dialog {
        return Dialog(context, R.style.MaterialDialogSheet).apply {
            setContentView(R.layout.dialog_full_image)
            setCancelable(true)
            window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            window!!.setBackgroundDrawableResource(R.color.TRANSPARENT)

            val imageView = this.findViewById<ImageView>(R.id.ivImage)
            val textView = this.findViewById<TextView>(R.id.tvTitle)

            value.url?.let { glideSetImage(imageView, it) }
            textView.text = value.title

            show()
        }
    }

    fun glideSetImage(imageView: ImageView, url: String) {
        Glide.with(imageView).load(url).apply(
            RequestOptions.placeholderOf(
                R.mipmap.ic_launcher
            )
        ).addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Handler(Looper.getMainLooper()).post {
                    glideSetImage(imageView, url)
                }
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        }).into(imageView)
    }
}