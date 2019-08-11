package chapter12

import com.badlogic.gdx.scenes.scene2d.Stage

class Treasure(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/treasure-chest.png")
    }
}
