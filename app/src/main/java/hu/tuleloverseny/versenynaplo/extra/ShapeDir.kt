package hu.tuleloverseny.versenynaplo.extra

import android.graphics.Color

class ShapeDir {
    companion object {
        private val shapeI: Shape = Shape(
            listOf(Position(-1,0), Position(0, 0), Position(1, 0), Position(2,0)),
            Color()
        )
        private val shapeL: Shape = Shape(
            listOf(Position(-1,-1), Position(-1,0), Position(0,0), Position(1,0)),
            Color()
        )
        private val shapel: Shape = Shape(
            listOf(Position(1,-1), Position(-1,0), Position(0,0), Position(1,0)),
            Color()
        )
        private val shapeB: Shape = Shape(
            listOf(Position(0,0), Position(1,0), Position(0,1), Position(1,1)),
            Color()
        )
        private val shapeS: Shape = Shape(
            listOf(Position(0,-1), Position(1,-1), Position(-1,0), Position(0,0)),
            Color()
        )
        private val shapeZ: Shape = Shape(
            listOf(Position(-1,-1), Position(0,-1), Position(0,0), Position(1,0)),
            Color()
        )
        private val shapeT: Shape = Shape(
            listOf(Position(0,-1), Position(-1,0), Position(0,0), Position(1,0)),
            Color()
        )

        private val pointNameToShape: Map<String, Shape> = mapOf(
            "001" to shapeI,
            "002" to shapeL,
            "003" to shapel,
            "004" to shapeB,
            "005" to shapeS,
            "006" to shapeZ,
            "007" to shapeT,
            "008" to shapeI,
            "009" to shapeL,
            "010" to shapel,
            "011" to shapeB,
            "012" to shapeS,
            "013" to shapeZ,
            "014" to shapeT,
            "015" to shapeI,
            "016" to shapeL,
            "017" to shapel,
            "018" to shapeB,
            "019" to shapeS,
            "020" to shapeZ,
            "021" to shapeT,
            "022" to shapeI,
            "023" to shapeL,
            "024" to shapel,
            "025" to shapeB,
            "026" to shapeS,
            "027" to shapeZ,
            "028" to shapeT
        )

        fun hasShape(pointName: String) = pointNameToShape.containsKey(pointName)

        fun getShape(pointName: String) = pointNameToShape.getOrElse(pointName, {shapeI})
    }
}