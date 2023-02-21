package com.judahben149.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.judahben149.customviews.databinding.FileDescriptorLayoutBinding
import java.io.File
import java.text.SimpleDateFormat
import kotlin.math.absoluteValue

enum class FileType {
    PDF, MP3, IMAGE, MP4, TEXT, DOCX, UNKNOWN
}

class FileDescriptor @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attrs, defStyleAttr) {

    private var _binding: FileDescriptorLayoutBinding? = null
    private val binding get() = _binding!!

    var showInfoByDefault = false
    @DrawableRes var viewBackground: Int = R.drawable.round_file_descriptor_background

    var fileUri: Uri? = null
    set(value) {
        field = value
        setupFileDescriptor()
    }

    var file: File? = null
    var fileType: FileType? = null

    init {
        _binding = FileDescriptorLayoutBinding.inflate(LayoutInflater.from(context), this)

        val attributes = ctx.obtainStyledAttributes(attrs, R.styleable.FileDescriptor)
        showInfoByDefault = attributes.getBoolean(R.styleable.FileDescriptor_showInfoByDefault, showInfoByDefault)
        viewBackground = attributes.getResourceId(R.styleable.FileDescriptor_viewBackground, viewBackground)

        attributes.recycle()

        setBackgroundResource(viewBackground)

        binding.tvFileInfo.visibility = if (showInfoByDefault) View.VISIBLE else View.GONE

        binding.ivInfoButton.setOnClickListener {
            showInfoByDefault = !showInfoByDefault
            binding.tvFileInfo.visibility = if (showInfoByDefault) View.VISIBLE else View.GONE
        }

        binding.ivShareButton.setOnClickListener {
            shareDescriptor()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupFileDescriptor() {
        setFile()
        setUpFileType()

        file?.let {
            binding.tvFileName.text = it.name
            binding.tvFileInfo.text = """
                File Name: ${ it.name }
                Path to file: ${ it.path }
                Last modified: ${ it.lastModified().format("MMM, dd, YYYY hh:mm") }
                Size: ${ it.length().valueInKb() }Kb
            """.trimIndent()
        }

        retrieveThumbnail()
    }

    private fun retrieveThumbnail() {
        try {
            when(fileType) {
                FileType.MP4 -> ThumbnailGenerator.generateVideoThumbnail(ctx, fileUri, binding.filePreviewImage)
                FileType.IMAGE -> ThumbnailGenerator.generateImageThumbnail(ctx, fileUri, binding.filePreviewImage)
                FileType.PDF -> binding.filePreviewImage.setImageResource(R.drawable.pdf)
                FileType.MP3 -> binding.filePreviewImage.setImageResource(R.drawable.mp3)
                FileType.DOCX -> binding.filePreviewImage.setImageResource(R.drawable.docx)
                FileType.TEXT -> binding.filePreviewImage.setImageResource(R.drawable.txt)
                FileType.UNKNOWN -> binding.filePreviewImage.setImageResource(R.drawable.file_unknown_svgrepo_com)
                else -> binding.filePreviewImage.setImageResource(R.drawable.file_unknown_svgrepo_com)
            }
        } catch (ex: Exception) {
            Log.d("FileDescriptor", "retrieveThumbnail: ${ ex.message }")
        }
    }

    private fun setFile() {
        val columns = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = ctx.contentResolver.query(fileUri!!, columns, null, null, null)
        cursor?.let {
            it.moveToFirst()
            val dataColumnIndex = it.getColumnIndex(columns[0])
            val filePath = cursor.getString(dataColumnIndex)
            it.close()
            filePath?.let { path ->
                file = File(path)
            }
        }
    }

    private fun setUpFileType() {
        val resolver = ctx.contentResolver
        val type = resolver.getType(fileUri!!)!!

        fileType = if (type.contains("image")) {
            FileType.IMAGE
        } else if (type.contains("video")) {
            FileType.MP4
        } else if (type.contains("application/msword") || type.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            FileType.DOCX
        } else if (type.contains("audio")) {
            FileType.MP3
        } else if (type.contains("application/pdf")) {
            FileType.PDF
        } else if (type.contains("text/plain")) {
            FileType.TEXT
        } else {
            FileType.UNKNOWN
        }

        setUpFileTypeImage()
    }

    private fun setUpFileTypeImage() {
        binding.fileTypeImage.setImageResource(when(fileType) {
            FileType.DOCX -> R.drawable.docx
            FileType.MP3 -> R.drawable.mp3
            FileType.MP4 -> R.drawable.video
            FileType.PDF -> R.drawable.pdf
            FileType.IMAGE -> R.drawable.image
            FileType.UNKNOWN -> R.drawable.question_mark_circle
            else -> R.drawable.question_mark_circle
        })
    }

    private fun shareDescriptor() {
        file?.let {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, binding.tvFileInfo.text)
                type = "text/plain"
            }

            ctx.startActivity(Intent.createChooser(shareIntent, null))
            return
        }

        Snackbar.make(this, "Select a file first", Snackbar.LENGTH_LONG).show()
    }

    fun Long.format(format: String): String {
        return SimpleDateFormat(format).format(this.absoluteValue)
    }

    fun Long.valueInKb(): Double {
        return div(1024).toDouble()
    }
}