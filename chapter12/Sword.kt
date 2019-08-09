package chapter12

import com.badlogic.gdx.scenes.scene2d.Stage

class Sword(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/sword.png")
    }
}
