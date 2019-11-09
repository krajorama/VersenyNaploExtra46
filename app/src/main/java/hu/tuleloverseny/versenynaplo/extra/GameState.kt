package hu.tuleloverseny.versenynaplo.extra

const val PLAYING_FIELD_NO_UPDATE = 0
const val PLAYING_FIELD_UPDATED = 1
const val PLAYING_FIELD_UPDATED_AND_FINAL = 2

class GameState(val RowCount: Int = Config.rowCount, val ColumnCount: Int = Config.columnCount) {
    private val history: MutableList<PlayingField> = mutableListOf(PlayingField(RowCount, ColumnCount))


    fun addNextShape(shape: Shape): Boolean {
        updateHistory(current().addNextShape(shape))
        return !current().isActiveShapeConflict()
    }

    fun addFinalShape(shape: PlacedShape) {
        val playingField: PlayingField = current().cloneAndMove(shape.activate())
        val scored: Pair<PlayingField, Int> = playingField.score()
        replaceHistory(scored.first)
    }

    fun moveActiveLeft(): Boolean = updateHistory(history.first().moveActiveLeft())

    fun moveActiveRight(): Boolean = updateHistory(history.first().moveActiveRight())

    fun moveActiveDown(): Int {
        val updated = updateHistory(history.first().moveActiveDown())
        return if (updated) {
            PLAYING_FIELD_UPDATED
        } else {
            if (current().isActiveShapeConflict()) {
                PLAYING_FIELD_NO_UPDATE
            } else {
                val scored: Pair<PlayingField, Int> = history.first().score()
                updateHistory(scored.first)
                PLAYING_FIELD_UPDATED_AND_FINAL
            }
        }
    }

    fun rotate(): Boolean = updateHistory(history.first().rotate())

    private fun updateHistory(playingField: PlayingField): Boolean {
        return if (history.first() === playingField) {
            false
        } else {
            history.add(0, playingField)
            true
        }
    }

    private fun replaceHistory(playingField: PlayingField) {
        history.removeAt(0)
        history.add(0, playingField)
    }

    fun current(): PlayingField = history.first()

    fun undo(): Boolean {
        return if (history.size > 2) {
            history.removeAt(0)
            true
        } else {
            false
        }
    }

    fun getPlayerMove(): PlayerMove = history.first().getPlayerMove()
}
