package chapter12

import com.badlogic.gdx.scenes.scene2d.Stage

class Arrow(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/arrow.png")
        setSpeed(400f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }
}
