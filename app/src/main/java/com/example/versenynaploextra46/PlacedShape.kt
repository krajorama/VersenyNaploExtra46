package com.example.versenynaploextra46

class PlacedShape(val shape: Shape, val position: Position) {
    val blocks = shape.blocks.map {it.add(position)}

    fun placed(): Shape = shape.moveTo(position)

    fun isOutside(limits: Position): Boolean =
        blocks.find {it.x < 0 || it.y < 0 || it.x >= limits.x || it.y >= limits.y} != null

    fun overwrite(limits: Position): Position =
        Position(this.horizontalOverwrite(limits), this.verticalOverwrite(limits))

    private fun horizontalOverwrite(limits: Position): Int {
        val limit = limits.x
        return shape.blocks.fold(0, { acc, shape_pos ->
            val pos = shape_pos.add(position)
            if ((pos.x < 0 && -shape_pos.x > acc) || (pos.x >= limit && -shape_pos.x < acc)) -shape_pos.x else acc
        })
    }

    private fun verticalOverwrite(limits: Position): Int {
        val limit = limits.y
        return shape.blocks.fold(0, { acc, shape_pos ->
            val pos = shape_pos.add(position)
            if ((pos.y < 0 && -shape_pos.y > acc) || (pos.y >= limit && -shape_pos.y < acc)) -shape_pos.y else acc
        })
    }
}
