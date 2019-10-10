package hu.tuleloverseny.versenynaplo.extra

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

class ShapeContainer(val lowCorner: Position, val highCorner: Position) {
    constructor(corners: Pair<Position, Position>) : this(corners.first, corners.second)
}

open class Shape(val blocks: List<Position>, val color: Int = Color.GREEN) {
    val container: ShapeContainer =
        ShapeContainer(
            blocks.fold(
                Pair(
                    Position(Int.MAX_VALUE, Int.MAX_VALUE),
                    Position(Int.MIN_VALUE, Int.MIN_VALUE)
                )
            ) { acc, pos ->
                Pair(
                    Position(
                        if (pos.x < acc.first.x) pos.x else acc.first.x,
                        if (pos.y < acc.first.y) pos.y else acc.first.y
                    ),
                    Position(
                        if (pos.x > acc.second.x) pos.x else acc.second.x,
                        if (pos.y > acc.second.y) pos.y else acc.second.y
                    )
                )
            })

    fun rotate(): Shape {
        return Shape(blocks.map {
            Position(
                it.y,
                -it.x
            )
        }, color)
    }

    override fun toString(): String = String.format("#%02x%02x%02x", color.red, color.green, color.blue) +
            ";" + blocks.joinToString(separator = ";") {it.toString()}

    companion object {
        fun fromString(text: String): Shape {
            val tokenLists = text.split(';').map {it.split(',')}
            val colorString = tokenLists.filter { it.size == 1 && it[0].startsWith("#") }.map { it[0] }
            return Shape(
                tokenLists.filter { it.size == 2 }.map{ Position(it) },
                Color.parseColor(if (colorString.isNotEmpty()) colorString[0] else "green")
            )
        }
    }
}
