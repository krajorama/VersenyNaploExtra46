package hu.tuleloverseny.versenynaplo.extra

import android.graphics.Color

class ShapeDir {
    companion object {
        private val shapeA: Shape = Shape(
            listOf(Position(-1,0), Position(0, 0), Position(1, 0), Position(2,0)),
            'A',
            Color.parseColor("cyan")
        )
        private val shapeB: Shape = Shape(
            listOf(Position(-1,-1), Position(-1,0), Position(0,0), Position(1,0)),
            'B',
            Color.parseColor("#FFA500")
        )
        private val shapeC: Shape = Shape(
            listOf(Position(1,-1), Position(-1,0), Position(0,0), Position(1,0)),
            'C',
            Color.parseColor("blue")
        )
        private val shapeD: Shape = Shape(
            listOf(Position(0,0), Position(1,0), Position(0,1), Position(1,1)),
            'D',
            Color.parseColor("yellow")
        )
        private val shapeE: Shape = Shape(
            listOf(Position(0,-1), Position(1,-1), Position(-1,0), Position(0,0)),
            'E',
            Color.parseColor("green")
        )
        private val shapeF: Shape = Shape(
            listOf(Position(-1,-1), Position(0,-1), Position(0,0), Position(1,0)),
            'F',
            Color.parseColor("red")
        )
        private val shapeG: Shape = Shape(
            listOf(Position(0,-1), Position(-1,0), Position(0,0), Position(1,0)),
            'G',
            Color.parseColor("purple")
        )

        private val labelToShape: Map<Char, Shape> = mapOf(
            'A' to shapeA,
            'B' to shapeB,
            'C' to shapeC,
            'D' to shapeD,
            'E' to shapeE,
            'F' to shapeF,
            'G' to shapeG
        )

        private val pointNameToLabel: Map<String, Char> = mapOf(
            "001" to 'A',
            "002" to 'B',
            "003" to 'C',
            "004" to 'D',
            "005" to 'E',
            "006" to 'F',
            "007" to 'G',
            "NH99" to 'A',
            "NH10" to 'D',
            "RAD" to 'F'
        )

        fun hasShape(pointName: String) = pointNameToLabel.containsKey(pointName)

        fun getShape(pointName: String) = labelToShape.getOrElse(
            pointNameToLabel.getOrElse(pointName, {'A'}), { shapeA}
        )

        fun getByLabel(label: Char) = labelToShape.getOrElse(label, { shapeA})
    }
}
