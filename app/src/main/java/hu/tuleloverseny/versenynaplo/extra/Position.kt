package hu.tuleloverseny.versenynaplo.extra

class Position(val x: Int = 0, val y: Int = 0) {
    fun add(other: Position) =
        Position(x = x + other.x, y = y + other.y)

    fun v_add(value: Int): Position =
        Position(x = x, y = y + value)
    fun h_add(value: Int): Position =
        Position(x = x + value, y = y)
}
