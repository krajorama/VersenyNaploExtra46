package com.example.versenynaploextra46

class PlacedShape(val shape: Shape, val position: Position) {
    val blocks = shape.blocks.map {it.add(position)}
    private val container =
        ShapeContainer(shape.container.lowCorner.add(position), shape.container.highCorner.add(position))

    fun overwrite(limits: Position): Position =
        Position(
            // X
            if (container.lowCorner.x < 0)
                -container.lowCorner.x
            else
                (if (container.highCorner.x >= limits.x) limits.x-1-container.highCorner.x else 0),
            // Y
            if (container.lowCorner.y < 0)
                -container.lowCorner.y
            else
                (if (container.highCorner.y >= limits.y) limits.y-1-container.highCorner.y else 0)
        )
}
