package com.example.versenynaploextra46

import android.graphics.Color

open class Shape(val blocks: List<Position>, val color: Color) {
    fun rotate(): Shape {
        return Shape(blocks.map{Position(it.y, -it.x)}, color)
    }

    /* constructor(shape: Shape) : this(shape.blocks, shape.color) */

    fun moveTo(position: Position): Shape = Shape(blocks.map {it.add(position)}, color)
}
