package hu.tuleloverseny.versenynaplo.extra

class PlayerMove(val info1: String, val info2: String, val info3: String) {
    constructor(pos: Position, rotation: Int): this(
        pos.x.toString(), pos.y.toString(), rotation.toString()
    )

    constructor(): this("", "", "")
    constructor(aChar: Char): this(aChar.toString(), aChar.toString(), aChar.toString())
}
