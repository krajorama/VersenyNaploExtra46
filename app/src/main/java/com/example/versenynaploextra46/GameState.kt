package com.example.versenynaploextra46

class GameState(val RowCount: Int = 12, val ColumnCount: Int = 6) {
    val history: MutableList<PlayingField> = mutableListOf(PlayingField(RowCount,ColumnCount))
    var validState = true
    var score = 0

    fun addNextShape(shape: Shape): Boolean = updateHistory(history.first().addNextShape(shape))

    fun moveActiveLeft(): Boolean = updateHistory(history.first().moveActiveLeft())

    fun moveActiveRight(): Boolean = updateHistory(history.first().moveActiveRight())

    fun moveActiveDown(): Boolean = updateHistory(history.first().moveActiveDown())

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
}
