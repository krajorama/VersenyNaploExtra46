package com.example.versenynaploextra46

class PlayingField(
    val RowCount: Int,
    val ColumnCount: Int,
    val blocks: Array<Array<ShapeRef>>)
{
    class ShapeRef(var shape: Shape? = null, var activeShape: Shape? = null)

    constructor(RowCount: Int, ColumnCount: Int): this(
        RowCount,
        ColumnCount,
        Array(RowCount) {Array(ColumnCount) { ShapeRef() } }
    )

    private val limits: Position = Position(x = ColumnCount, y = RowCount)
    var activeShapeRef: PlacedShape? = null

    fun addNextShape(shape: Shape): PlayingField {
        assert(activeShapeRef == null)
        val startPosition = Position(x = ColumnCount/2, y = 0)
        val initialShape = PlacedShape(shape, startPosition)
        val overwrite = initialShape.overwrite(limits)
        return cloneAndPlace(PlacedShape(shape, startPosition.add(overwrite)))
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

    fun moveActiveLeft(): PlayingField = moveActive(dX=-1)

    fun moveActiveRight(): PlayingField = moveActive(dX=1)

    private fun moveActive(dX: Int): PlayingField {
        val placedShape: PlacedShape? = this.activeShapeRef

        if (placedShape != null) {
            val newPlacedShape = PlacedShape(placedShape.shape, placedShape.position.h_add(dX))
            if (!newPlacedShape.isOutside(limits) && !isPlacedShapeConflict(newPlacedShape)) {
                return cloneAndPlace(newPlacedShape)
            }
        }
        return this
    }

    fun moveActiveDown(): PlayingField {
        val placedShape: PlacedShape? = this.activeShapeRef

        if (placedShape != null) {
            val newPlacedShape = PlacedShape(placedShape.shape, placedShape.position.v_add(value=1))
            return if (newPlacedShape.isOutside(limits) || isPlacedShapeConflict(newPlacedShape)) {
                doFinalize(newPlacedShape)
            } else {
                cloneAndPlace(newPlacedShape)
            }
        }
        return this
    }

    private fun doFinalize(placedShape: PlacedShape): PlayingField {
        return this
    }

    fun rotate(): PlayingField {
        val placedShape: PlacedShape? = this.activeShapeRef

        if (placedShape != null) {
            val rotatedShape = PlacedShape(placedShape.shape.rotate(), placedShape.position)
            val overwrite = rotatedShape.overwrite(limits)
            val newPlacedShape = PlacedShape(rotatedShape.shape, rotatedShape.position.add(overwrite))
            if (!newPlacedShape.isOutside(limits) && !isPlacedShapeConflict(newPlacedShape)) {
                return cloneAndPlace(newPlacedShape)
            }
        }

        return this
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

    fun finalizePlacedShape() {
        val placedShape: PlacedShape? = this.activeShapeRef
        this.activeShapeRef = null

        if (placedShape != null) {
            for (pos in placedShape.blocks) {
                assert(getShapeRef(pos).shape == null)
                getShapeRef(pos).shape = placedShape.shape
                getShapeRef(pos).activeShape = null
            }
        }
    }

    private fun getShapeRef(position: Position): ShapeRef = blocks[position.y][position.x]
}