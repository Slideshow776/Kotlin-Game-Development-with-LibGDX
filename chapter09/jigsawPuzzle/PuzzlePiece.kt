package chapter09.jigsawPuzzle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class PuzzlePiece(x: Float, y: Float, s: Stage) : DragAndDropActor(x, y, s) {
    var row: Int = 0
    var col: Int = 0

    enum class Rotations { // Number of rotations by 90 degrees clock-wise, rotating a THREE makes it a ZERO.
        ZERO, ONE, TWO, THREE
    }

    private lateinit var rotations: Rotations

    private val clickClickSound = Gdx.audio.newSound(Gdx.files.internal("assets/click-click.wav"))
    private val lowWhooshSound = Gdx.audio.newSound(Gdx.files.internal("assets/low-whoosh.wav"))
    private val whooshSound = Gdx.audio.newSound(Gdx.files.internal("assets/whoosh.wav"))
    private val tingSound = Gdx.audio.newSound(Gdx.files.internal("assets/ting.wav"))

    var puzzleArea: PuzzleArea? = null

    val isCorrectlyPlaced: Boolean
        get() = (hasPuzzleArea()
                && this.row == puzzleArea!!.row
                && this.col == puzzleArea!!.col
                && rotations == Rotations.ZERO)

    init {
        setRandomRotation()

        // animation
        val randomDelay = MathUtils.random(0f, 1f)
        addAction(Actions.sequence(
            Actions.moveBy(0f, 620f, 0f),
            Actions.delay(randomDelay),
            Actions.moveTo(x, y, 2f, Interpolation.bounceOut)
        ))
    }

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
                lowWhooshSound.play()
            }
        } else {
            whooshSound.play()
        }
    }

    override fun doubleTap() {
        super.doubleTap()
        addAction(Actions.rotateBy(-90f, .25f))
        rotations = when (rotations) {
            Rotations.ZERO -> Rotations.ONE
            Rotations.ONE -> Rotations.TWO
            Rotations.TWO -> Rotations.THREE
            Rotations.THREE -> Rotations.ZERO
        }
    }

    private fun setRandomRotation() {
        val randomIndex = MathUtils.random(0, Rotations.values().size - 1)
        val randomType = Rotations.values()[randomIndex]
        setRotation(randomType)
    }

    private fun setRotation(r: Rotations) {
        rotations = r
        when (r) {
            Rotations.ZERO -> addAction(Actions.rotateBy(0f, .25f))
            Rotations.ONE -> addAction(Actions.rotateBy(-90f, .25f))
            Rotations.TWO -> addAction(Actions.rotateBy(-180f, .25f))
            Rotations.THREE -> addAction(Actions.rotateBy(-270f, .25f))
        }
    }
}
