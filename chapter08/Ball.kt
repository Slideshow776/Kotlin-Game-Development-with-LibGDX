package chapter08

import com.badlogic.gdx.scenes.scene2d.Stage
import kotlin.math.abs

class Ball(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private var paused: Boolean = true

    init {
        loadTexture("assets/ball.png")
        setSpeed(400f)
        setMotionAngle(90f)
        setBoundaryPolygon(12)
    }

    fun isPaused() = paused
    fun setPaused(b: Boolean) { paused = b }

    override fun act(dt: Float) {
        super.act(dt)

        if (!isPaused()) {
            // simulate gravity
            setAcceleration(10f)
            accelerateAtAngle(270f)
            applyPhysics(dt)
        }
    }

    fun bounceOff(other: BaseActor) {
        val v = this.preventOverlap(other)
        if (abs(v!!.x) >= abs(v.y)) {
            this.velocityVec.x *= -1
        } else { // vertical bounce
            this.velocityVec.y *= -1
        }

    }
}
