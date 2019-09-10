package com.example.versenynaploextra46

class PlayingField(val RowCount: Int, val ColumnCount: Int, val blocks: Array<Array<ShapeRef>>) {
    class ShapeRef(var shape: Shape? = null, var activeShape: Shape? = null)

    constructor(RowCount: Int, ColumnCount: Int): this(
        RowCount,
        ColumnCount,
        Array(RowCount) {Array(ColumnCount) { ShapeRef() } }
    )

    private val limits: Position = Position(x = ColumnCount, y = RowCount)
    var activeShape: PlacedShape? = null

    fun addNextShape(shape: Shape) {
        assert(activeShape == null)
        val startPosition = Position(x = ColumnCount/2, y = 0)
        val initialShape = PlacedShape(shape, startPosition)
        val overwrite = initialShape.overwrite(limits)
        placeActiveShape(PlacedShape(shape, startPosition.add(overwrite)))
    }

    fun moveActiveLeft(): Boolean = moveActive(dX=-1)

    fun moveActiveRight(): Boolean = moveActive(dX=1)

    private fun moveActive(dX: Int): Boolean {
        val placedShape: PlacedShape? = this.activeShape

        if (placedShape != null) {
            val newPlacedShape = PlacedShape(placedShape.shape, placedShape.position.h_add(dX))
            if (!newPlacedShape.isOutside(limits) && !isPlacedShapeConflict(newPlacedShape)) {
                return placeActiveShape(newPlacedShape)
            }
        }
        return false
    }

    fun moveActiveDown(): Boolean {
        val placedShape: PlacedShape? = this.activeShape

        if (placedShape != null) {
            val newPlacedShape = PlacedShape(placedShape.shape, placedShape.position.v_add(value=1))
            return if (newPlacedShape.isOutside(limits) || isPlacedShapeConflict(newPlacedShape)) {
                doFinalize(newPlacedShape)
            } else {
                placeActiveShape(newPlacedShape)
            }
        }
        return false
    }

    private fun doFinalize(placedShape: PlacedShape): Boolean {
        finalizePlacedShape()
        return false
    }

    fun rotate(): Boolean {
        val placedShape: PlacedShape? = this.activeShape

        if (placedShape != null) {
            val rotatedShape = PlacedShape(placedShape.shape.rotate(), placedShape.position)
            val overwrite = rotatedShape.overwrite(limits)
            val newPlacedShape = PlacedShape(rotatedShape.shape, rotatedShape.position.add(overwrite))
            if (!newPlacedShape.isOutside(limits) && !isPlacedShapeConflict(newPlacedShape)) {
                return placeActiveShape(newPlacedShape)
            }
        }

        return false
    }

    private fun placeActiveShape(placedShape: PlacedShape): Boolean {
        clearActiveShape()
        for (pos in placedShape.blocks) {
            getShapeRef(pos).activeShape = placedShape.shape
        }
        this.activeShape = placedShape
        return true
    }

    private fun clearActiveShape() {
        val placedShape: PlacedShape? = this.activeShape
        this.activeShape = null

        if ( placedShape != null) {
            for (pos in placedShape.blocks) {
                getShapeRef(pos).activeShape = null
            }
        }
    }

    fun isActiveShapeConflict(): Boolean {
        val shape: PlacedShape? = this.activeShape

        return if (shape != null) isPlacedShapeConflict(shape) else false
    }

    private fun isPlacedShapeConflict(placedShape: PlacedShape): Boolean =
        placedShape.blocks.find{getShapeRef(it).shape != null} != null

    fun finalizePlacedShape() {
        val placedShape: PlacedShape? = this.activeShape
        this.activeShape = null

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