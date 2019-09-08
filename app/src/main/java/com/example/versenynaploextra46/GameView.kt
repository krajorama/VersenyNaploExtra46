package com.example.versenynaploextra46

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs


class GameView(context: Context, attrs: AttributeSet): View(context, attrs) {
    var gameState = GameState()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val blockSize: Int = arrayOf(this.width / gameState.ColumnCount,
            this.height / gameState.RowCount).min() ?: 0
        val width = blockSize * gameState.ColumnCount
        val height = blockSize * gameState.RowCount
        val leftMargin = (this.width - width) / 2
        val topMargin = (this.height - height + 1)

        var shapeDrawable: ShapeDrawable

        if (canvas != null) {
            for ((row, rowArray) in gameState.current().blocks.withIndex()) {
                for ((column, block) in rowArray.withIndex()) {
                    if (block.activeShape != null) {
                        shapeDrawable = ShapeDrawable(RectShape())
                        shapeDrawable.setBounds(
                            leftMargin + column * blockSize,
                            topMargin + row * blockSize,
                            leftMargin + (column + 1) * blockSize,
                            topMargin + (row + 1) * blockSize
                        )
                        shapeDrawable.paint.color = Color.parseColor("#009944")
                        shapeDrawable.draw(canvas)
                    }
                }
            }
        }
    }


    private val myListener =  object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1 != null && e2 != null) {
                val dX = e2.rawX - e1.rawX
                val dY = e2.rawY - e1.rawY

                if (abs(dX) < abs(dY)) {
                    if (dY < 0) {
                        // up
                        if (gameState.rotate())
                            this@GameView.invalidate()
                    } else {
                        // down
                        if (gameState.moveActiveDown())
                            this@GameView.invalidate()
                    }
                } else {
                    if(dX < 0) {
                        // left
                        if (gameState.moveActiveLeft())
                            this@GameView.invalidate()
                    } else {
                        // right
                        if (gameState.moveActiveRight())
                            this@GameView.invalidate()
                    }
                }
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    private val detector: GestureDetector = GestureDetector(context, myListener)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    fun doUndo() {
        if (gameState.undo())
            this.invalidate()
    }
}
