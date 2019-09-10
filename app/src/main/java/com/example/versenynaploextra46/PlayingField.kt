package com.example.versenynaploextra46

import android.graphics.Color
import android.inputmethodservice.Keyboard

class PlayingField(
    private val RowCount: Int,
    private val ColumnCount: Int,
    val blocks: Array<Array<ShapeRef>>)
{
    class ShapeRef(var shape: Shape? = null, var activeShape: Shape? = null)

    companion object {
        val borderShapeRef: ShapeRef = ShapeRef(Shape(emptyList(), Color()), null)
    }

    constructor(RowCount: Int, ColumnCount: Int): this(
        RowCount,
        ColumnCount,
        Array(RowCount) {Array(ColumnCount) { ShapeRef() } }
    )

    private val limits: Position = Position(x = ColumnCount, y = RowCount)
    var activeShapeRef: PlacedShape? = null

    fun addNextShape(shape: Shape): PlayingField {
        val placedShape: PlacedShape? = mayMoveActive()

        return if (placedShape == null) {
            val startPosition = Position(x = ColumnCount / 2, y = 0)
            val initialShape = PlacedShape(shape, startPosition)
            val overwrite = initialShape.overwrite(limits)
            cloneAndPlace(PlacedShape(shape, startPosition.add(overwrite)))
        } else {
            this
        }
    }

    fun moveActiveLeft(): PlayingField = moveActive(dX=-1, dY=0)

    fun moveActiveRight(): PlayingField = moveActive(dX=1, dY=0)

    fun moveActiveDown(): PlayingField = moveActive(dX=0, dY=1)

    private fun moveActive(dX: Int, dY: Int): PlayingField {
        val movableShape: PlacedShape? = mayMoveActive()

        return if (movableShape != null) {
            val newPlacedShape = PlacedShape(movableShape.shape, movableShape.position.add(Position(dX, dY)))
            if (!isPlacedShapeConflict(newPlacedShape))
                cloneAndPlace(newPlacedShape)
            else
                this
        } else {
            this
        }
    }

    fun rotate(): PlayingField {
        val movableShape: PlacedShape? = mayMoveActive()

        return if (movableShape != null) {
            val rotatedShape = PlacedShape(movableShape.shape.rotate(), movableShape.position)
            val overwrite = rotatedShape.overwrite(limits)
            val newPlacedShape = PlacedShape(rotatedShape.shape, rotatedShape.position.add(overwrite))
            if (!isPlacedShapeConflict(newPlacedShape))
                cloneAndPlace(newPlacedShape)
            else
                this
        } else {
            this
        }
    }

    private fun mayMoveActive(): PlacedShape? {
        val placedShape: PlacedShape? = this.activeShapeRef
        return if (placedShape != null && !isPlacedShapeConflict(placedShape)) placedShape else null
    }

    private fun cloneAndPlace(placedShape: PlacedShape): PlayingField {
        return clone().placeActiveShape(placedShape)
    }

    private fun clone(): PlayingField {
        val newBlocks: Array<Array<ShapeRef>> = Array(RowCount) {
                row: Int -> Array(ColumnCount) {
                col: Int -> ShapeRef(this.blocks[row][col].shape, null)
        }}
        return PlayingField(RowCount, ColumnCount, newBlocks)
    }

    private fun placeActiveShape(placedShape: PlacedShape): PlayingField {
        for (pos in placedShape.blocks) {
            getShapeRef(pos).activeShape = placedShape.shape
        }
        this.activeShapeRef = placedShape
        return this
    }

    fun isActiveShapeConflict(): Boolean {
        val shape: PlacedShape? = this.activeShapeRef

        return if (shape != null) isPlacedShapeConflict(shape) else false
    }

    private fun isPlacedShapeConflict(placedShape: PlacedShape): Boolean =
        placedShape.blocks.find{getShapeRef(it).shape != null} != null

    private fun finalizePlacedShape(): PlayingField {
        val placedShape: PlacedShape? = this.activeShapeRef
        this.activeShapeRef = null

        if (placedShape != null) {
            for (pos in placedShape.blocks) {
                getShapeRef(pos).shape = placedShape.shape
                getShapeRef(pos).activeShape = null
            }
        }
        return this
    }

    private fun getShapeRef(position: Position): ShapeRef =
        if (position.y < 0 || position.x < 0 || position.y >= limits.y || position.x >= limits.x)
            borderShapeRef
        else
            blocks[position.y][position.x]

    fun score(): Pair<PlayingField, Int> {
        val finalField: PlayingField = clone()

        val placedShape: PlacedShape? = this.activeShapeRef
        if (placedShape != null) finalField.placeActiveShape(placedShape)

        return finalField.finalizePlacedShape().doScore()
    }

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
                    Array(ColumnCount) {ShapeRef()}
                } else {
                    scoreAndRemaining.second[rowIdx - RowCount + scoreAndRemaining.second.size]
            }}
            Pair(PlayingField(RowCount, ColumnCount, resultBlocks), scoreAndRemaining.first)
        }
    }
}