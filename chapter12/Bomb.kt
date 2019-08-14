package chapter12

import com.badlogic.gdx.scenes.scene2d.Stage

class Bomb(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/bomb.png", 2, 3, .4f, false)
        setSize(40f, 40f)
    }
}
