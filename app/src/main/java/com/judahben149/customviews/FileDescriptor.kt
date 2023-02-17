package com.judahben149.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout

class FileDescriptor @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attrs, defStyleAttr) {


    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.file_descriptor_layout, this)
    }
}