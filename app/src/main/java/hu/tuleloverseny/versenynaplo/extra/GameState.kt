package hu.tuleloverseny.versenynaplo.extra

const val PLAYING_FIELD_UPDATED = 1
const val PLAYING_FIELD_UPDATED_AND_FINAL = 2

class GameState(val RowCount: Int = 12, val ColumnCount: Int = 6) {
    private val history: MutableList<PlayingField> = mutableListOf(PlayingField(RowCount, ColumnCount))

    // var validState = true
    var score = 0

    fun addNextShape(shape: Shape): Boolean = updateHistory(history.first().addNextShape(shape))

    fun moveActiveLeft(): Boolean = updateHistory(history.first().moveActiveLeft())

    fun moveActiveRight(): Boolean = updateHistory(history.first().moveActiveRight())

    fun moveActiveDown(): Int {
        val updated = updateHistory(history.first().moveActiveDown())
        return if (!updated) {
            val scored: Pair<PlayingField, Int> = history.first().score()
            score += scored.second
            updateHistory(scored.first)
            PLAYING_FIELD_UPDATED_AND_FINAL
        } else {
            PLAYING_FIELD_UPDATED
        }
    }

    fun rotate(): Boolean = updateHistory(history.first().rotate())

    private fun updateHistory(playingField: PlayingField): Boolean {
        return if (!(history.first() === playingField)) {
            history.add(0, playingField)
            true
        } else {
            false
        }
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
