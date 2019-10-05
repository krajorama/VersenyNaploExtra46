package hu.tuleloverseny.versenynaplo.extra

import android.graphics.Color

class ShapeContainer(val lowCorner: Position, val highCorner: Position) {
    constructor(corners: Pair<Position, Position>) : this(corners.first, corners.second)
}

open class Shape(val blocks: List<Position>, val color: Color) {
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
}
