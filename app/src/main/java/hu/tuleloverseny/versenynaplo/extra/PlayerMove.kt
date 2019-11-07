package hu.tuleloverseny.versenynaplo.extra

class PlayerMove(val info1: String, val info2: String, val info3: String) {
    constructor(placedShape: PlacedShape): this(
        info1="P,${placedShape.shape.label}",
        info2="${placedShape.position.x},${placedShape.position.y}",
        info3="${placedShape.rotation}"
    )

    constructor(): this("", "", "")
    //constructor(aChar: Char): this(aChar.toString(), aChar.toString(), aChar.toString())

    fun isValidEntry(): Boolean = isValidPlacement() || isFull()

    private fun isValidPlacement(): Boolean = info1.startsWith("P,")
    fun isFull(): Boolean = info1.startsWith("T")

    fun getShape(): PlacedShape =
        PlacedShape(ShapeDir.getByLabel(getShapeNumber()), getPosition()).rotateTo(getRotation())

    private fun getShapeNumber(): Char {
        val tokens = info1.split(",")
        return tokens[1][0]
    }

    private fun getPosition(): Position {
        val tokens = info2.split(",")
        return Position(tokens[0].toInt(), tokens[1].toInt())
    }

    private fun getRotation(): Int = info3.toInt()

    companion object {
        fun gameIsFull() = PlayerMove("T","0,0","0")
    }
}
