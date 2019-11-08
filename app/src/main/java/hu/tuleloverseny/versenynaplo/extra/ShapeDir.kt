package hu.tuleloverseny.versenynaplo.extra

import android.graphics.Color

class ShapeDir {
    companion object {
        private val shapeA: Shape = Shape(
            // OX
            listOf(Position(0,0), Position(1, 0)),
            'A',
            Color.parseColor("#7FFFD4")
        )
        private val shapeB: Shape = Shape(
            // XOXX
            listOf(Position(-1,0), Position(0,0), Position(1,0), Position(2,0)),
            'B',
            Color.parseColor("#00FFFF")
        )
        private val shapeC: Shape = Shape(
            // OX
            // XX
            listOf(Position(0,0), Position(1,0),
                Position(0,1), Position(1,1)),
            'C',
            Color.parseColor("#FFFF00")
        )
        private val shapeD: Shape = Shape(
            // X
            // XOX
            listOf(Position(-1,-1),
                Position(-1,0), Position(0,0), Position(1,0)),
            'D',
            Color.parseColor("#0000FF")
        )
        private val shapeE: Shape = Shape(
            //   X
            // XOX
            listOf(Position(1,-1),
                Position(-1,0), Position(0,0), Position(1,0)),
            'E',
            Color.parseColor("#FFA500")
        )
        private val shapeF: Shape = Shape(
            // XX
            //  OX
            listOf(Position(-1,-1), Position(0,-1),
                Position(0,0), Position(1,0)),
            'F',
            Color.parseColor("#FF0000")
        )
        private val shapeG: Shape = Shape(
            //  XX
            // XO
            listOf(Position(0,-1), Position(1,-1),
                Position(-1,0), Position(0,0)),
            'G',
            Color.parseColor("#00FF00")
        )
        private val shapeH: Shape = Shape(
            //  X
            // XOX
            listOf(
                Position(0,-1),
                Position(-1,0), Position(0,0), Position(1,0)),
            'H',
            Color.parseColor("#800080")
        )
        private val shapeJ: Shape = Shape(
            // X X
            //  O
            // X X
            listOf(
                Position(-1,-1), Position(1,-1),
                Position(0,0),
                Position(-1,1), Position(1,1)
            ),
            'J',
            Color.parseColor("#FF00FF")
        )
        private val shapeK: Shape = Shape(
            //  X
            // XOX
            //  X
            listOf(
                Position(0,-1),
                Position(-1,0), Position(0,0), Position(1,0),
                Position(0,1)
            ),
            'K',
            Color.parseColor("#008080")
        )
        private val shapeL: Shape = Shape(
            // XXX
            // XoX
            // XXX
            listOf(
                Position(-1,-1), Position(0,-1), Position(1,-1),
                Position(-1,0), Position(1,0),
                Position(-1,1), Position(0,1), Position(1,1)
            ),
            'L',
            Color.parseColor("#808000")
        )
        private val shapeM: Shape = Shape(
            // XOXX
            // X  X
            listOf(
                Position(-1,0), Position(0,0), Position(1,0), Position(2,0),
                Position(-1,1), Position(2,1)
            ),
            'M',
            Color.parseColor("#F0E68C")
        )
        private val shapeN: Shape = Shape(
            // XX XX
            //  XOX
            // XX XX
            listOf(
                Position(-2,-1), Position(-1,-1), Position(1,-1), Position(2,-1),
                Position(-1,0), Position(0,0), Position(1,0),
                Position(-2,1), Position(-1,1), Position(1,1), Position(2,1)
            ),
            'N',
            Color.parseColor("#FF6347")
        )
        private val shapeP: Shape = Shape(
            //  O
            // X X
            listOf(
                Position(0,0),
                Position(-1,1), Position(1,1)
            ),
            'P',
            Color.parseColor("#FAEBD7")
        )
        private val shapeT: Shape = Shape(
            listOf(Position(0,0)),
            'T',
            Color.parseColor("#00BFFF")
        )

        private val labelToShape: Map<Char, Shape> = listOf(
            shapeA,
            shapeB,
            shapeC,
            shapeD,
            shapeE,
            shapeF,
            shapeG,
            shapeH,
            shapeJ,
            shapeK,
            shapeL,
            shapeM,
            shapeN,
            shapeP,
            shapeT
        ).map { it.label to it }.toMap()

        private val pointNameToLabel: Map<String, Char> = mapOf(
            "001" to 'A',
            "002" to 'B',
            "003" to 'C',
            "004" to 'D',
            "005" to 'E',
            "006" to 'F',
            "007" to 'G',
            "008" to 'H',
            "009" to 'J',
            "010" to 'K',
            "011" to 'L',
            "012" to 'M',
            "013" to 'N',
            "014" to 'P',
            "TM" to 'T'
        )

        private val kisHalalNameToLabel: Map<String, Char> = mapOf(
            "1" to 'B',
            "2" to 'M',
            "3" to 'K',
            "4" to 'A',
            "5" to 'C',
            "6" to 'E',
            "7" to 'H',
            "8" to 'D',
            "9" to 'F',
            "10" to 'B',
            "11" to 'C',
            "12" to 'P',
            "13" to 'J',
            "14" to 'H',
            "15" to 'N',
            "16" to 'H',
            "17" to 'E',
            "18" to 'E',
            "19" to 'F',
            "20" to 'D',
            "21" to 'H',
            "22" to 'A',
            "23" to 'K',
            "24" to 'G',
            "25" to 'E',
            "26" to 'C',
            "27" to 'F',
            "28" to 'L',
            "29" to 'G',
            "30" to 'A',
            "31" to 'G',
            "32" to 'A',
            "33" to 'E',
            "34" to 'H',
            "35" to 'H',
            "36" to 'D',
            "37" to 'G',
            "38" to 'G',
            "39" to 'B',
            "40" to 'M',
            "41" to 'A',
            "42" to 'H',
            "43" to 'D',
            "44" to 'F',
            "45" to 'F',
            "46" to 'F',
            "47" to 'J',
            "48" to 'G',
            "49" to 'D',
            "50" to 'D',
            "51" to 'C',
            "52" to 'K',
            "53" to 'H',
            "54" to 'D',
            "55" to 'A',
            "56" to 'N',
            "57" to 'B',
            "58" to 'C',
            "59" to 'B',
            "60" to 'D',
            "61" to 'H',
            "62" to 'E',
            "63" to 'P',
            "64" to 'F',
            "65" to 'E',
            "66" to 'A',
            "67" to 'C',
            "68" to 'F',
            "69" to 'L',
            "70" to 'M',
            "71" to 'A',
            "72" to 'D',
            "73" to 'L',
            "74" to 'A',
            "75" to 'B',
            "76" to 'P',
            "77" to 'B',
            "78" to 'L',
            "79" to 'G',
            "80" to 'N',
            "81" to 'B',
            "82" to 'C',
            "83" to 'D',
            "84" to 'J',
            "85" to 'G',
            "86" to 'H',
            "87" to 'C',
            "88" to 'C',
            "89" to 'E',
            "90" to 'E',
            "91" to 'B',
            "92" to 'F',
            "TM" to 'T'
        )

        private val kozepHalalNameToLabel: Map<String, Char> = mapOf(
            "1" to 'A',
            "TM" to 'T'
        )

        private val nagyHalalNameToLabel: Map<String, Char> = mapOf(
            "1" to 'C',
            "TM" to 'T'
        )

        private val emptyCategory: Map<String, Char> = emptyMap()

        private val categoryToMap: Map<String, Map<String, Char>> = mapOf(
            "KisH" to kisHalalNameToLabel,
            "KözH" to kozepHalalNameToLabel,
            "NH" to nagyHalalNameToLabel,
            "TM" to nagyHalalNameToLabel
        )

        fun hasShape(category: String, pointName: String): Boolean {
            val categoryMap = categoryToMap.getOrElse(category, { emptyCategory})
            return categoryMap.containsKey(pointName) || categoryMap.containsKey(pointName.dropWhile { it == '0' })
        }

        fun getShape(category: String, pointName: String): Shape {
            val pointNameToLabel = categoryToMap.getOrElse(category, { emptyCategory})
            val alternateName: String = pointName.dropWhile { it == '0' }
            val label: Char = if (pointNameToLabel.containsKey(pointName)) pointNameToLabel.getOrElse(pointName, {'T'})
                else pointNameToLabel.getOrElse(alternateName, {'T'})
            return labelToShape.getOrElse(label, { shapeT})
        }

        fun getByLabel(label: Char) = labelToShape.getOrElse(label, { shapeT})
    }
}
