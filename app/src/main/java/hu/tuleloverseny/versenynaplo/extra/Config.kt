package hu.tuleloverseny.versenynaplo.extra

//import java.io.File

class Config {
    companion object {
        var rowCount: Int = 15
        var columnCount: Int = 9
/*

        fun load(persistentDir: File?) {
            if (persistentDir == null)
                return

            val configFile = File(persistentDir, "config.txt")

            if (configFile.exists()) {
                loadData(configFile)
            } else {
                dumpData(configFile)
            }
        }

        private fun dumpData(configFile: File) {
            val printWriter = configFile.printWriter()
            printWriter.println("dimensions=$rowCount,$columnCount")
            ShapeDir.pointNameToShape.map { printWriter.println("$it") }
            printWriter.close()
        }

        private fun loadData(configFile: File) {
            val newShapeDir: MutableMap<String, Shape> = mutableMapOf()
            configFile.forEachLine {
                val keyValue: List<String> = it.split('=')
                if (keyValue.size == 2) {
                    val key: String = keyValue[0]
                    val value: String = keyValue[1]

                    if (key == "dimensions") {
                        val numbers: List<String> = value.split(',')
                        rowCount = numbers.getOrElse(0) { rowCount.toString() }.toIntOrNull() ?: rowCount
                        columnCount = numbers.getOrElse(1) { columnCount.toString() }.toIntOrNull() ?: columnCount
                    } else {
                        newShapeDir[key] = Shape.fromString(value)
                    }
                }
            }
            ShapeDir.pointNameToShape = newShapeDir
        }
*/
    }
}
