package chapter7

import com.badlogic.gdx.scenes.scene2d.Stage

class Sky(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/sky.png")
        setSpeed(25f)
        setMotionAngle(180f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)

        // if moved completely past left edge of the screen,
        // shift right, past other instance
        if (x + width < 0)
            moveBy(2 * width, 0f)
    }
}
