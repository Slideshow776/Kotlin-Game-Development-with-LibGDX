package chapter11

import com.badlogic.gdx.scenes.scene2d.Stage

class Lock(x: Float, y: Float, s: Stage) : Solid(x, y, 32f, 32f, s) {
    init {
        loadTexture("assets/items/lock.png")
    }
}
