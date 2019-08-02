package chapter11

import com.badlogic.gdx.scenes.scene2d.Stage

class Coin(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet(
            "assets/items/coin.png",
            1, 6,
            .1f,
            true
        )
    }
}
