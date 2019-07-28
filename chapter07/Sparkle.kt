package chapter07

import com.badlogic.gdx.scenes.scene2d.Stage

class Sparkle(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet(
            "assets/sparkle.png",
            8,
            8,
            .02f,
            false
        )
    }

    override fun act(dt: Float) {
        super.act(dt)
        if (isAnimationFinished())
            remove()
    }
}
