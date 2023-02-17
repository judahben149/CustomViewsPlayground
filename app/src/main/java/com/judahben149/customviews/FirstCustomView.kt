package com.judahben149.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StyleRes
import kotlinx.coroutines.delay
import java.lang.Thread.sleep
import java.util.*
import kotlin.random.Random

class FirstCustomView @JvmOverloads
constructor(
    private val ctx: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
    private val defStyleRes: Int = 0
) : View(ctx, attributeSet, defStyleAttr, defStyleRes) {

    var rectPaint = Paint()
    var circlePaint = Paint()

    var outlinedRectPaint = Paint()

    var linePaint = Paint()


    val colorList = listOf(Color.BLACK, Color.YELLOW, Color.RED, Color.MAGENTA, Color.BLUE)

    init {
        rectPaint.color = Color.GREEN
        rectPaint.isAntiAlias = true

        circlePaint.color = Color.BLUE
        circlePaint.isAntiAlias = true

        with(outlinedRectPaint) {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

        linePaint.color = Color.BLACK
        linePaint.strokeWidth = 7f

    }

    fun getRandomIndex(): Int {
        return Random.nextInt(0, colorList.size - 1)
    }

    fun toggleColors() {
        rectPaint.color = colorList[getRandomIndex()]
        circlePaint.color = colorList[getRandomIndex()]
        outlinedRectPaint.color = colorList[getRandomIndex()]

        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawLine(canvas)
        drawRectangle(canvas)
        drawCircle(canvas)

        drawSecondLine(canvas)
        drawOutlinedRectangle(canvas)
    }

    private fun drawOutlinedRectangle(canvas: Canvas?) {
        canvas?.translate(width-100f, 250f)
        canvas?.drawRect(0f, 0f, 100f, 100f, outlinedRectPaint)
    }

    fun drawRectangle(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, 200f, 200f, rectPaint)
    }

    fun drawCircle(canvas: Canvas?) {
        canvas?.drawCircle(width - 50f, 200f, 50f, circlePaint)
    }

    fun drawLine(canvas: Canvas?) {
        canvas?.drawLine(0f, 0f, width - 0f, height - 0f, linePaint)
    }

    fun drawSecondLine(canvas: Canvas?) {
        canvas?.drawLine(width - 0f, 0f, 0f, height - 0f, linePaint)
    }
}