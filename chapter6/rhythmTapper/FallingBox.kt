package chapter6.rhythmTapper

import com.badlogic.gdx.scenes.scene2d.Stage

class FallingBox(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/box.png")
        setScale(.75f, .75f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }
}
