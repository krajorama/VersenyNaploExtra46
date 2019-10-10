package hu.tuleloverseny.versenynaplo.extra

import android.graphics.Color

class ShapeDir {
    companion object {
        private val shapeI: Shape = Shape(
            listOf(Position(-1,0), Position(0, 0), Position(1, 0), Position(2,0)),
            Color.parseColor("cyan")
        )
        private val shapeL: Shape = Shape(
            listOf(Position(-1,-1), Position(-1,0), Position(0,0), Position(1,0)),
            Color.parseColor("#FFA500")
        )
        private val shapel: Shape = Shape(
            listOf(Position(1,-1), Position(-1,0), Position(0,0), Position(1,0)),
            Color.parseColor("blue")
        )
        private val shapeB: Shape = Shape(
            listOf(Position(0,0), Position(1,0), Position(0,1), Position(1,1)),
            Color.parseColor("yellow")
        )
        private val shapeS: Shape = Shape(
            listOf(Position(0,-1), Position(1,-1), Position(-1,0), Position(0,0)),
            Color.parseColor("green")
        )
        private val shapeZ: Shape = Shape(
            listOf(Position(-1,-1), Position(0,-1), Position(0,0), Position(1,0)),
            Color.parseColor("red")
        )
        private val shapeT: Shape = Shape(
            listOf(Position(0,-1), Position(-1,0), Position(0,0), Position(1,0)),
            Color.parseColor("purple")
        )

        var pointNameToShape: Map<String, Shape> = mapOf(
            "001" to shapeI,
            "002" to shapeL,
            "003" to shapel,
            "004" to shapeB,
            "005" to shapeS,
            "006" to shapeZ,
            "007" to shapeT
        )

        fun hasShape(pointName: String) = pointNameToShape.containsKey(pointName)

        fun getShape(pointName: String) = pointNameToShape.getOrElse(pointName, {shapeI})
    }
}