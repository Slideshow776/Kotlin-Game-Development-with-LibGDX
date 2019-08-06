package chapter11

import com.badlogic.gdx.scenes.scene2d.Stage

class Spike(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture(("assets/items/spike.png"))
    }
}
