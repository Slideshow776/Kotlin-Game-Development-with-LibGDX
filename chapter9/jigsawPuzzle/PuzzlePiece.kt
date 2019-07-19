package chapter9.jigsawPuzzle

import com.badlogic.gdx.scenes.scene2d.Stage

class PuzzlePiece(x: Float, y: Float, s: Stage) : DragAndDropActor(x, y, s) {
    var row: Int = 0
    var col: Int = 0

    var puzzleArea: PuzzleArea? = null

    val isCorrectlyPlaced: Boolean
        get() = (hasPuzzleArea()
                && this.row == puzzleArea!!.row
                && this.col == puzzleArea!!.col)

    fun clearPuzzleArea() { puzzleArea = null }
    fun hasPuzzleArea(): Boolean { return puzzleArea != null }

    override fun onDragStart() {
        super.onDragStart()
        if (hasPuzzleArea()) {
            val pa = puzzleArea
            pa!!.setTargetable(true)
            clearPuzzleArea()
        }
    }

    override fun onDrop() {
        super.onDrop()
        if (hasDropTarget()) {
            val pa = dropTarget as PuzzleArea?
            moveToActor(pa!!)
            puzzleArea = pa
            pa.setTargetable(false)
        }
    }
}
