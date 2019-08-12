package chapter12

import com.badlogic.gdx.scenes.scene2d.Stage

class ShopArrow(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/arrow-icon.png")
    }
}
