package hu.tuleloverseny.versenynaplo.extra

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import kotlin.math.abs


class GameView(context: Context, attrs: AttributeSet): View(context, attrs) {
    var gameState = GameState()
    var doneButton: ImageButton? = null

    private val blockDrawable = ShapeDrawable(RectShape())
    private val ovalDrawable = ShapeDrawable(OvalShape())

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val blockSize: Int = arrayOf(this.width / gameState.ColumnCount,
            this.height / gameState.RowCount).min() ?: 0
        val width = blockSize * gameState.ColumnCount
        val height = blockSize * gameState.RowCount
        val leftMargin = (this.width - width) / 2
        val topMargin = (this.height - height + 1)

        if (canvas != null) {
            ovalDrawable.paint.color = Color.BLACK
            blockDrawable.setBounds(
                leftMargin,
                topMargin,
                leftMargin + width,
                topMargin + height
            )
            blockDrawable.paint.color = ContextCompat.getColor(context, R.color.colorRowAsked)
            blockDrawable.draw(canvas)

            for ((row, rowArray) in gameState.current().blocks.withIndex()) {
                for ((column, blockRef) in rowArray.withIndex()) {
                    if (blockRef.shape != null || blockRef.activeShape != null) {
                        val shape: Shape = blockRef.activeShape?.shape ?: blockRef.shape!!
                        blockDrawable.setBounds(
                            leftMargin + column * blockSize + 1,
                            topMargin + row * blockSize + 1,
                            leftMargin + (column + 1) * blockSize - 1,
                            topMargin + (row + 1) * blockSize - 1
                        )
                        blockDrawable.paint.color = Color.parseColor("green")
                        blockDrawable.draw(canvas)

                        if (blockRef.activeShape != null) {
                            val outerBounds = blockDrawable.bounds
                            ovalDrawable.setBounds(
                                outerBounds.left + outerBounds.width()/3,
                                outerBounds.top + outerBounds.height()/3,
                                outerBounds.right - outerBounds.width()/3,
                                outerBounds.bottom - outerBounds.height()/3
                            )
                            ovalDrawable.draw(canvas)
                        }
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

                val needUpdate: Boolean =
                    if (abs(dX) < abs(dY)) {
                        if (dY < 0) {
                            // up
                            gameState.rotate()
                        } else {
                            // down
                            val update: Int = gameState.moveActiveDown()
                            if (update == PLAYING_FIELD_UPDATED_AND_FINAL)
                                enableDoneButton()

                            (update != PLAYING_FIELD_NO_UPDATE)
                        }
                    } else {
                        if (dX < 0) {
                            // left
                            gameState.moveActiveLeft()
                        } else {
                            // right
                            gameState.moveActiveRight()
                        }
                    }
                if (needUpdate) this@GameView.invalidate()
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    private val detector: GestureDetector = GestureDetector(context, myListener)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    fun doUndo() {
        if (gameState.undo()) {
            disableDoneButton()
            this.invalidate()
        }
    }

    fun getExtraInfo1(): String = getPlayerMove().info1
    fun getExtraInfo2(): String = getPlayerMove().info2
    fun getExtraInfo3(): String = getPlayerMove().info3

    private fun getPlayerMove(): PlayerMove = gameState.getPlayerMove()

    fun enableDoneButton() {
        doneButton?.isClickable = true
        doneButton?.setBackgroundColor(
            ContextCompat.getColor(
                this@GameView.context,
                R.color.play_button_enabled
            )
        )
    }

    fun disableDoneButton() {
        doneButton?.isClickable = false
        doneButton?.setBackgroundColor(
            ContextCompat.getColor(
                this@GameView.context,
                R.color.play_button_disabled
            )
        )
    }
}
