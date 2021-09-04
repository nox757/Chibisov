package ru.chibisovap.chibisov.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

interface GifLoader {
    fun load(
        url: String,
        mImageView: ImageView,
        onLoadingFailed: () -> Unit,
        onLoadingFinished: () -> Unit
    )
}

class GlideGifLoader : GifLoader {

    override fun load(
        url: String,
        mImageView: ImageView,
        onLoadingFailed: () -> Unit,
        onLoadingFinished: () -> Unit
    ) {

        val listener = object : RequestListener<GifDrawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<GifDrawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFailed()
                return false
            }

            override fun onResourceReady(
                resource: GifDrawable?,
                model: Any?,
                target: Target<GifDrawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }
        }

        Glide.with(mImageView.context)
            .asGif()
            .load(url)
            .listener(listener)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mImageView)

    }
}

