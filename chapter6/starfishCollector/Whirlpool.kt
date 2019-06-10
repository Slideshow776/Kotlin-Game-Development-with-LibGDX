package chapter6.starfishCollector

import com.badlogic.gdx.scenes.scene2d.Stage

private var x: Float? = null
private var y: Float? = null
private var s: Stage? = null

class Whirlpool(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/whirlpool.png", 2, 5, .1f, false)
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (isAnimationFinished())
            remove()
    }
}