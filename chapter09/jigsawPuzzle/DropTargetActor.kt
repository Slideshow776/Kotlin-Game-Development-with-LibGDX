package chapter09.jigsawPuzzle

import com.badlogic.gdx.scenes.scene2d.Stage

open class DropTargetActor(x: Float, y: Float, s: Stage): BaseActor(x, y, s) {
    private var targetable = true

    fun setTargetable(t: Boolean) { targetable = t }
    fun isTargetable(): Boolean { return targetable }
}
