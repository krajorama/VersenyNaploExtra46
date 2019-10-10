package hu.tuleloverseny.versenynaplo.extra

class Position(val x: Int = 0, val y: Int = 0) {
    fun add(other: Position) =
        Position(x = x + other.x, y = y + other.y)

    fun v_add(value: Int): Position =
        Position(x = x, y = y + value)
    fun h_add(value: Int): Position =
        Position(x = x + value, y = y)

    override fun toString(): String = "$x,$y"

    constructor(posStrings: List<String>):
            this(posStrings[0].toIntOrNull() ?: 0, posStrings[1].toIntOrNull() ?: 0)
}
