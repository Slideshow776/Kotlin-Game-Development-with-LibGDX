package chapter6.spaceRocks

import com.badlogic.gdx.scenes.scene2d.Stage

class Thrusters(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/fire.png")
    }
}