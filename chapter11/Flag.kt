package chapter11

import com.badlogic.gdx.scenes.scene2d.Stage

class Flag(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/items/flag.png", 1, 2, .2f, true)
    }
}
