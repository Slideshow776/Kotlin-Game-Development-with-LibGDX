package chapter9.jigsawPuzzle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage

class PuzzlePiece(x: Float, y: Float, s: Stage) : DragAndDropActor(x, y, s) {
    var row: Int = 0
    var col: Int = 0

    private val clickClickSound = Gdx.audio.newSound(Gdx.files.internal("assets/click-click.wav"))
    private val lowWooshSound = Gdx.audio.newSound(Gdx.files.internal("assets/low-whoosh.wav"))
    private val wooshSound = Gdx.audio.newSound(Gdx.files.internal("assets/whoosh.wav"))
    private val tingSound = Gdx.audio.newSound(Gdx.files.internal("assets/ting.wav"))

    var puzzleArea: PuzzleArea? = null

    val isCorrectlyPlaced: Boolean
        get() = (hasPuzzleArea()
                && this.row == puzzleArea!!.row
                && this.col == puzzleArea!!.col)

    fun clearPuzzleArea() { puzzleArea = null }
    fun hasPuzzleArea(): Boolean { return puzzleArea != null }

    override fun onDragStart() {
        super.onDragStart()
        clickClickSound.play()
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

            if (isCorrectlyPlaced) {
                tingSound.play(.05f)
            } else {
                lowWooshSound.play()
            }
        } else {
            wooshSound.play()
        }
    }
}
