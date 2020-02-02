package com.anwesh.uiprojects.stepbouncypolylineview

/**
 * Created by anweshmishra on 02/02/20.
 */

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color

val nodes : Int = 5
val lines : Int = 6
val scGap : Float = 0.02f / lines
val strokeFactor : Float = 90f
val sizeFactor : Float = 2.9f
val delay : Long = 20
val foreColor : Int = Color.parseColor("#4CAF50")
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawStepBouncyPolyLine(i : Int, scale : Float, size : Float, paint : Paint) {
    val sf : Float = scale.sinify().divideScale(i, lines)
    val deg : Float = 360f / lines
    save()
    rotate(deg * sf)
    drawLine(0f, 0f, 0f, -size, paint)
    restore()
}

fun Canvas.drawStepBouncyPolyLines(scale : Float, size : Float, paint : Paint) {
    val scDiv : Double = 1.0 / lines
    val k : Int = Math.floor(scale.sinify() / scDiv).toInt()
    for (j in 0..k) {
        drawStepBouncyPolyLine(j, scale, size, paint)
    }
}

fun Canvas.drawSBPLNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (nodes + 1)
    paint.color = foreColor
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    val size : Float = gap / sizeFactor
    save()
    translate(w / 2, gap * (i + 1))
    drawStepBouncyPolyLines(scale, size, paint)
    restore()
}
