package chapter09.jigsawPuzzle

import com.badlogic.gdx.scenes.scene2d.Stage

class PuzzleArea(x: Float, y: Float, s: Stage) : DropTargetActor(x, y, s) {
    var row: Int = 0
    var col: Int = 0

    init {
        loadTexture("assets/border.jpg")
    }
}
