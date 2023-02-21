package com.judahben149.customviews

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide

object ThumbnailGenerator {

    fun generateVideoThumbnail(context: Context, imageUri: Uri?, imageView: ImageView) {
        Glide.with(context).asBitmap().load(imageUri).into(imageView)
    }

    fun generateImageThumbnail(context: Context, imageUri: Uri?, imageView: ImageView) {
        imageView.setImageBitmap(
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        )
    }
}