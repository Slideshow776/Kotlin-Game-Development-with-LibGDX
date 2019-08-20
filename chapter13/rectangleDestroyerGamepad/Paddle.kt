package chapter13.rectangleDestroyerGamepad

import com.badlogic.gdx.scenes.scene2d.Stage

class Paddle(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/paddle.png")
    }
}
