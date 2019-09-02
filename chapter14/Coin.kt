package chapter14

import com.badlogic.gdx.scenes.scene2d.Stage

class Coin(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/coin.png")
        setBoundaryPolygon(6)
    }
}
