package chapter11

import com.badlogic.gdx.scenes.scene2d.Stage

/*
* Assumes all parallax background images fill the whole screen, and moves from right to left
* */
class Parallax(x: Float, y: Float, s: Stage, texture: String, speed: Float): BaseActor(x, y, s) {
    init {
        loadTexture(texture)
        setSpeed(speed)
        setMotionAngle(180f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)

        // if moved completely past left edge of the screen
        // shift right, past other instance
        if (x + width < 0)
            moveBy(2 * width, 0f)
    }
}
