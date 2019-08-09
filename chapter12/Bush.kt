package chapter12

import com.badlogic.gdx.scenes.scene2d.Stage

class Bush(x: Float, y: Float, s: Stage) : Solid(x, y, 32f, 32f, s) {
    init {
        loadTexture("assets/bush.png")
        setBoundaryPolygon(8)
    }
}
