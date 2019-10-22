package chapter16.rectangleDestroyer3D

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs

open class Ball (x: Float, y: Float, z: Float, s: Stage3D) : Sphere(x, y, z, s) {
    private var paused: Boolean = true
    protected var velocityVec: Vector2 = Vector2(0f, 0f)
    private var accelerationVec: Vector2 = Vector2(0f, 0f)
    private var acceleration: Float = 0f
    private var maxSpeed: Float = 1000f
    private var deceleration: Float = 0f

    init {
        setColor(Color.LIGHT_GRAY)
        setScale(.6f, .6f, .6f)

        setSpeed(6f)
        setMotionAngle(90f)
        setBasePolygon()
    }

    fun isPaused() = paused
    fun setPaused(b: Boolean) { paused = b }

    override fun act(dt: Float) {
        super.act(dt)

        if (!isPaused()) {
            // simulate gravity
            setAcceleration(.1f)
            accelerateAtAngle(270f)
            applyPhysics(dt)
        }
    }

    fun bounceOff(other: BaseActor3D) {
        val v = this.preventOverlap(other)
        if (abs(v!!.x) >= abs(v.y)) {
            this.velocityVec.x *= -1
        } else { // vertical bounce
            this.velocityVec.y *= -1
        }
    }

    // Physics ---------------------------------------------------------------------------------------------------
    fun setSpeed(speed: Float) {
        // If length is zero, then assume motion angle is zero degrees
        if (velocityVec.len() == 0f)
            velocityVec.set(speed, 0f)
        else
            velocityVec.setLength(speed)
    }

    fun getSpeed() = velocityVec.len()
    fun setMotionAngle(angle: Float) = velocityVec.setAngle(angle)
    fun getMotionAngle() = velocityVec.angle()
    fun isMoving() = getSpeed() > 0

    fun setAcceleration(acc: Float) { acceleration = acc }
    fun accelerateAtAngle(angle: Float) = accelerationVec.add( Vector2(acceleration, 0f).setAngle(angle))
    fun accelerateForward() = accelerateAtAngle(getTurnAngle())
    fun setMaxSpeed(ms: Float) { maxSpeed = ms }
    fun setDeceleration(dec: Float) { deceleration = dec }

    fun applyPhysics(dt: Float) {
        // apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt)

        var speed = getSpeed()

        // decrease speed (decelerate) when not accelerating
        if (accelerationVec.len() == 0f)
            speed-= deceleration * dt

        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0f, maxSpeed)

        // update velocity
        setSpeed(speed)

        // apply velocity
        moveBy(velocityVec.x* dt, velocityVec.y * dt, 0f)

        // reset acceleration
        accelerationVec.set(0f, 0f)
    }
}
