package hu.tuleloverseny.versenynaplo.extra

import android.graphics.Color

class PlayingField(
    private val RowCount: Int,
    private val ColumnCount: Int,
    val blocks: Array<Array<ShapeRef>>,
    private val currentShape: PlacedShape
)
{
    class ShapeRef(var shape: Shape? = null, var activeShape: PlacedShape? = null)

    companion object {
        val borderShapeRef: ShapeRef = ShapeRef(shape = Shape(emptyList()))
    }

    private val limits: Position =
        Position(x = ColumnCount, y = RowCount)

    constructor(RowCount: Int, ColumnCount: Int): this(
        RowCount,
        ColumnCount,
        Array(RowCount) {Array(ColumnCount) { ShapeRef() } },
        PlacedShape(Shape(emptyList()), active = false)
    )

    fun addNextShape(shape: Shape): PlayingField {
        return if (currentShape.active) this
        else {
            val newPlacedShape = PlacedShape(shape, Position(x = ColumnCount / 2, y = 0), active = true)
            val overWrite: Position = newPlacedShape.overwrite(limits)
            val movedShape = PlacedShape(shape, newPlacedShape.position.add(overWrite), active = true)
            this.cloneAndMove(movedShape)
        }
    }

    fun dropDown(): PlayingField {
        val dropped: PlayingField = moveActiveDown()
        return if (dropped === this) this else dropped.dropDown()
    }

    fun moveActiveLeft(): PlayingField = moveActive(dX=-1, dY=0)

    fun moveActiveRight(): PlayingField = moveActive(dX=1, dY=0)

    fun moveActiveDown(): PlayingField = moveActive(dX=0, dY=1)

    private fun moveActive(dX: Int, dY: Int): PlayingField {
        return if (mayMoveActive()) {
            val newPlacedShape = PlacedShape(
                currentShape.shape,
                currentShape.position.add(Position(dX, dY)),
                currentShape.rotation,
                currentShape.active
            )
            if (!isPlacedShapeConflict(newPlacedShape))
                cloneAndMove(newPlacedShape)
            else
                this
        } else {
            this
        }
    }

    fun rotate(): PlayingField {
        return if (mayMoveActive()) {
            val rotatedShape =
                PlacedShape(
                    currentShape.shape.rotate(),
                    currentShape.position,
                    (currentShape.rotation + 1) % 4,
                    currentShape.active
                )
            val overwrite = rotatedShape.overwrite(limits)
            val newPlacedShape = PlacedShape(
                rotatedShape.shape,
                rotatedShape.position.add(overwrite),
                rotatedShape.rotation,
                rotatedShape.active
            )
            if (!isPlacedShapeConflict(newPlacedShape))
                cloneAndMove(newPlacedShape)
            else
                this
        } else {
            this
        }
    }

    fun hasActiveShape() = currentShape.active

    private fun mayMoveActive() = currentShape.active && !isPlacedShapeConflict(currentShape)

    private fun cloneAndPlace(): PlayingField {
        val newBlocks: Array<Array<ShapeRef>> = Array(RowCount) {
                row: Int -> Array(ColumnCount) {
                col: Int ->
            ShapeRef(shape = this.blocks[row][col].shape)
        }}
        for (pos in currentShape.blocks) {
            newBlocks[pos.y][pos.x].shape = currentShape.shape
        }
        return PlayingField(RowCount, ColumnCount, newBlocks, currentShape.deActivate())
    }

    fun cloneAndMove(placedShape: PlacedShape): PlayingField {
        val newBlocks: Array<Array<ShapeRef>> = Array(RowCount) {
                row: Int -> Array(ColumnCount) {
                col: Int ->
            ShapeRef(shape = this.blocks[row][col].shape)
        }}
        for (pos in placedShape.blocks) {
            newBlocks[pos.y][pos.x].activeShape = placedShape
        }
        return PlayingField(RowCount, ColumnCount, newBlocks, placedShape)
    }

    fun isActiveShapeConflict(): Boolean = currentShape.active && isPlacedShapeConflict(currentShape)

    private fun isPlacedShapeConflict(placedShape: PlacedShape): Boolean =
        placedShape.blocks.find{getShapeRef(it).shape != null} != null

    private fun getShapeRef(position: Position): ShapeRef =
        if (position.y < 0 || position.x < 0 || position.y >= limits.y || position.x >= limits.x)
            borderShapeRef
        else
            blocks[position.y][position.x]

    fun score(): Pair<PlayingField, Int> = finalizePlacedShape().doScore()

    private fun finalizePlacedShape(): PlayingField = if (currentShape.active) cloneAndPlace() else this

    private fun doScore(): Pair<PlayingField, Int> {
        fun scoringRow(row: Array<ShapeRef>): Boolean = row.find { it.shape == null } == null

        val scoreAndRemaining: Pair<Int, List<Array<ShapeRef>>> = blocks.fold(Pair(0, emptyList())) { acc, row ->
            if (scoringRow(row)) {
                Pair(acc.first + 1, acc.second)
            } else {
                Pair(acc.first, acc.second.plusElement(row))
            }
        }

        return if (scoreAndRemaining.second.size == RowCount) {
            Pair(this, 0)
        } else {
            val resultBlocks: Array<Array<ShapeRef>> = Array(RowCount) {
                rowIdx -> if (rowIdx < RowCount - scoreAndRemaining.second.size) {
                    Array(ColumnCount) { ShapeRef() }
                } else {
                    scoreAndRemaining.second[rowIdx - RowCount + scoreAndRemaining.second.size]
            }}
            Pair(PlayingField(RowCount, ColumnCount, resultBlocks, currentShape), scoreAndRemaining.first)
        }
    }

    fun getPlayerMove(): PlayerMove =
        if (currentShape.active) {
            if (isPlacedShapeConflict(currentShape)) PlayerMove('E') else PlayerMove()
        } else {
            PlayerMove(
                currentShape.position.x.toString(),
                currentShape.position.y.toString(),
                currentShape.rotation.toString()
            )
        }
}
