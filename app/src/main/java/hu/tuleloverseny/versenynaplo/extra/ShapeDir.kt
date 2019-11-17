package hu.tuleloverseny.versenynaplo.extra


class ShapeDir {
    companion object {
        private val shapeA: Shape = Shape(
            // OX
            listOf(Position(0,0), Position(1, 0)),
            'A'
        )
        private val shapeB: Shape = Shape(
            // XOXX
            listOf(Position(-1,0), Position(0,0), Position(1,0), Position(2,0)),
            'B'
        )
        private val shapeC: Shape = Shape(
            // OX
            // XX
            listOf(Position(0,0), Position(1,0),
                Position(0,1), Position(1,1)),
            'C'
        )
        private val shapeD: Shape = Shape(
            // X
            // XOX
            listOf(Position(-1,-1),
                Position(-1,0), Position(0,0), Position(1,0)),
            'D'
        )
        private val shapeE: Shape = Shape(
            //   X
            // XOX
            listOf(Position(1,-1),
                Position(-1,0), Position(0,0), Position(1,0)),
            'E'
        )
        private val shapeF: Shape = Shape(
            // XX
            //  OX
            listOf(Position(-1,-1), Position(0,-1),
                Position(0,0), Position(1,0)),
            'F'
        )
        private val shapeG: Shape = Shape(
            //  XX
            // XO
            listOf(Position(0,-1), Position(1,-1),
                Position(-1,0), Position(0,0)),
            'G'
        )
        private val shapeH: Shape = Shape(
            //  X
            // XOX
            listOf(
                Position(0,-1),
                Position(-1,0), Position(0,0), Position(1,0)),
            'H'
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
            'J'
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
            'K'
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
            'L'
        )
        private val shapeM: Shape = Shape(
            // XOXX
            // X  X
            listOf(
                Position(-1,0), Position(0,0), Position(1,0), Position(2,0),
                Position(-1,1), Position(2,1)
            ),
            'M'
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
            'N'
        )
        private val shapeP: Shape = Shape(
            //  O
            // X X
            listOf(
                Position(0,0),
                Position(-1,1), Position(1,1)
            ),
            'P'
        )
        private val shapeT: Shape = Shape(
            listOf(Position(0,0)),
            'T'
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
            "69" to 'G',
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
            "1" to 'H',
            "2" to 'N',
            "3" to 'J',
            "4" to 'H',
            "5" to 'F',
            "6" to 'D',
            "7" to 'B',
            "8" to 'D',
            "9" to 'P',
            "10" to 'B',
            "11" to 'B',
            "12" to 'K',
            "13" to 'M',
            "14" to 'C',
            "15" to 'N',
            "16" to 'F',
            "17" to 'C',
            "18" to 'F',
            "19" to 'E',
            "20" to 'E',
            "21" to 'F',
            "22" to 'H',
            "23" to 'J',
            "24" to 'G',
            "25" to 'D',
            "26" to 'G',
            "27" to 'F',
            "28" to 'L',
            "29" to 'A',
            "30" to 'B',
            "31" to 'D',
            "32" to 'H',
            "33" to 'B',
            "34" to 'A',
            "35" to 'E',
            "36" to 'E',
            "37" to 'E',
            "38" to 'H',
            "39" to 'C',
            "40" to 'L',
            "41" to 'C',
            "42" to 'G',
            "43" to 'E',
            "44" to 'F',
            "45" to 'N',
            "46" to 'D',
            "47" to 'K',
            "48" to 'G',
            "49" to 'F',
            "50" to 'A',
            "51" to 'A',
            "52" to 'P',
            "53" to 'B',
            "54" to 'G',
            "55" to 'M',
            "56" to 'P',
            "57" to 'B',
            "58" to 'A',
            "59" to 'A',
            "60" to 'H',
            "61" to 'D',
            "62" to 'A',
            "63" to 'J',
            "64" to 'G',
            "65" to 'H',
            "66" to 'C',
            "67" to 'K',
            "68" to 'G',
            "69" to 'C',
            "70" to 'J',
            "71" to 'M',
            "72" to 'C',
            "73" to 'P',
            "74" to 'C',
            "75" to 'G',
            "76" to 'M',
            "77" to 'E',
            "78" to 'L',
            "79" to 'K',
            "80" to 'N',
            "81" to 'H',
            "82" to 'C',
            "83" to 'A',
            "84" to 'L',
            "85" to 'D',
            "86" to 'F',
            "87" to 'D',
            "88" to 'B',
            "89" to 'H',
            "90" to 'A',
            "91" to 'B',
            "92" to 'E',
            "TM" to 'T'
        )

        private val nagyHalalNameToLabel: Map<String, Char> = mapOf(
            "1" to 'H',
            "2" to 'N',
            "3" to 'P',
            "4" to 'B',
            "5" to 'A',
            "6" to 'C',
            "7" to 'E',
            "8" to 'C',
            "9" to 'L',
            "10" to 'E',
            "11" to 'H',
            "12" to 'M',
            "13" to 'L',
            "14" to 'N',
            "15" to 'J',
            "16" to 'H',
            "17" to 'C',
            "18" to 'D',
            "19" to 'A',
            "20" to 'F',
            "21" to 'B',
            "22" to 'H',
            "23" to 'J',
            "24" to 'G',
            "25" to 'F',
            "26" to 'E',
            "27" to 'A',
            "28" to 'K',
            "29" to 'D',
            "30" to 'H',
            "31" to 'A',
            "32" to 'G',
            "33" to 'C',
            "34" to 'G',
            "35" to 'E',
            "36" to 'A',
            "37" to 'B',
            "38" to 'G',
            "39" to 'H',
            "40" to 'J',
            "41" to 'F',
            "42" to 'B',
            "43" to 'C',
            "44" to 'C',
            "45" to 'M',
            "46" to 'B',
            "47" to 'L',
            "48" to 'A',
            "49" to 'G',
            "50" to 'D',
            "51" to 'B',
            "52" to 'N',
            "53" to 'E',
            "54" to 'E',
            "55" to 'K',
            "56" to 'M',
            "57" to 'F',
            "58" to 'P',
            "59" to 'E',
            "60" to 'G',
            "61" to 'D',
            "62" to 'G',
            "63" to 'N',
            "64" to 'A',
            "65" to 'H',
            "66" to 'C',
            "67" to 'L',
            "68" to 'F',
            "69" to 'C',
            "70" to 'K',
            "71" to 'M',
            "72" to 'E',
            "73" to 'P',
            "74" to 'G',
            "75" to 'B',
            "76" to 'K',
            "77" to 'D',
            "78" to 'M',
            "79" to 'L',
            "80" to 'K',
            "81" to 'H',
            "82" to 'D',
            "83" to 'F',
            "84" to 'J',
            "85" to 'N',
            "86" to 'D',
            "87" to 'F',
            "88" to 'A',
            "89" to 'B',
            "90" to 'D',
            "91" to 'F',
            "92" to 'P',
            "TM" to 'T'
        )

        private val emptyCategory: Map<String, Char> = emptyMap()

        private val categoryToMap: Map<String, Map<String, Char>> = mapOf(
            "KisH" to kisHalalNameToLabel,
            "Kishalál" to kisHalalNameToLabel,
            "K" to kisHalalNameToLabel,
            "KözH" to kozepHalalNameToLabel,
            "Középhalál" to kozepHalalNameToLabel,
            "M" to kozepHalalNameToLabel,
            "NH" to nagyHalalNameToLabel,
            "Nagyhalál" to nagyHalalNameToLabel,
            "N" to nagyHalalNameToLabel,
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
