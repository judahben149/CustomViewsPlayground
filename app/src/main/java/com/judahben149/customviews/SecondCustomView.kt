package com.judahben149.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withTranslation

class SecondCustomView @JvmOverloads constructor(
    private val ctx: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
    private val defAttrRes: Int = 0
): View(ctx, attributeSet, defStyleAttr, defAttrRes) {

    var path = Path()
    var curvedPath = Path()

    var pathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#a20a0a")
    }

    var curvedPathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#892cdc")
    }

    var paint = Paint().apply {
        isAntiAlias = true
        color = Color.YELLOW

    }


    fun reInitializeCanvasParameters() {
        paint.color = Color.YELLOW

    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        drawLinedPath(canvas)
//        drawCurvedPath(canvas)

        drawSmileyEmoji(canvas)
//        drawFlower(canvas)
    }

    private fun drawFlower(canvas: Canvas?) {
        canvas?.withTranslation(0.5f * width, 0.5f * height) {

            for (i in 1..6) {
                curvedPath.quadTo(30f, 60f, 60f, 60f)
                canvas.drawPath(curvedPath, curvedPathPaint)
                canvas.rotate(60f)
            }
        }
    }

    private fun drawSmileyEmoji(canvas: Canvas?) {
        canvas?.drawCircle(0.5f * width, 0.5f * height, 300f, paint)

        canvas?.withTranslation((0.5f * width) - 100f, (0.5f * height) - 100f) {
            paint.color = Color.GREEN
            canvas.drawCircle(0f, 0f, 40f, paint)
        }

        canvas?.withTranslation ((0.5f * width) + 100f, (0.5f * height) - 100f) {
            paint.color = Color.GREEN
            canvas.drawCircle(0f, 0f, 40f, paint)
        }

        canvas?.withTranslation ((0.5f * width), (0.5f * height) + 30f) {
            curvedPath.moveTo(-200f, 0f)
            curvedPath.quadTo(0f, 250f, 200f , 0f)
            canvas.drawPath(curvedPath, curvedPathPaint)
        }
    }

    private fun drawLinedPath(canvas: Canvas?) {
        path.moveTo(0.1f * width, 0.1f * height)
        path.lineTo(0.1f * width, 0.5f * height)
        path.lineTo(0.9f * width, 0.1f * height)

//        path.moveTo(0.1f * width, 0.05f * height)
        path.lineTo(width.toFloat(), 0.5f * height)
        path.lineTo(0.5f * width, 0.25f * height)

        canvas?.drawPath(path, pathPaint)
    }

    private fun drawCurvedPath(canvas: Canvas?) {
        curvedPath.moveTo(0.5f * width, 0.5f * height)

        curvedPath.quadTo(0.7f * width, 0.7f * height, 0.9f * width, 0.5f * height)

        canvas?.drawPath(curvedPath, curvedPathPaint)
    }
}