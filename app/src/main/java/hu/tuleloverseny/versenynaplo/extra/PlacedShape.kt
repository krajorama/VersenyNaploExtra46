package hu.tuleloverseny.versenynaplo.extra

class PlacedShape(val shape: Shape, val position: Position = Position(), val rotation: Int = 0, val active: Boolean = false) {
    val blocks = shape.blocks.map {it.add(position)}
    private val container =
        ShapeContainer(
            shape.container.lowCorner.add(position),
            shape.container.highCorner.add(position)
        )

    fun overwrite(limits: Position): Position =
        Position(
            // X
            if (container.lowCorner.x < 0)
                -container.lowCorner.x
            else
                (if (container.highCorner.x >= limits.x) limits.x - 1 - container.highCorner.x else 0),
            // Y
            if (container.lowCorner.y < 0)
                -container.lowCorner.y
            else
                (if (container.highCorner.y >= limits.y) limits.y - 1 - container.highCorner.y else 0)
        )

    fun deActivate(): PlacedShape = PlacedShape(shape, position, rotation, false)
    fun activate(): PlacedShape = PlacedShape(shape, position, rotation, true)
    fun rotateTo(rotate: Int): PlacedShape =
        if (rotate>0)
            PlacedShape(shape.rotate(), position, rotation + 1, active).rotateTo(rotate - 1)
        else
            this

    fun addUUID(value: String): PlacedShape = PlacedShape(shape.addUUID(value), position, rotation, active)
}
