package chapter11

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import kotlin.math.abs

class Koala(x: Float, y: Float, s: Stage): BaseActor(x, y, s) {
    private lateinit var stand: Animation<TextureRegion>
    private lateinit var walk: Animation<TextureRegion>
    private lateinit var jump: Animation<TextureRegion>

    private val walkAcceleration = 100f
    private val walkDeceleration = 200f
    private val maxHorizontalSpeed = 200f
    private val gravity = 700f
    private val maxVerticalSpeed = 1000f

    private val jumpSpeed = 450f
    private lateinit var belowSensor: BaseActor

    init {
        stand = loadTexture("assets/koala/stand.png")

        val walkFileNames: Array<String> = Array()
        walkFileNames.add("assets/koala/walk-1.png")
        walkFileNames.add("assets/koala/walk-2.png")
        walkFileNames.add("assets/koala/walk-3.png")
        walkFileNames.add("assets/koala/walk-2.png")
        walk = loadAnimationFromFiles(walkFileNames, .2f, true)

        jump = loadTexture("assets/koala/jump.png")
        setBoundaryPolygon(6)
        belowSensor = BaseActor(0f, 0f, s)
        belowSensor.loadTexture("assets/white.png")
        belowSensor.setSize(this.width - 8f, 8f)
        belowSensor.setBoundaryRectangle()
        belowSensor.isVisible = true
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (Gdx.input.isKeyPressed(Keys.LEFT))
            accelerationVec.add(-walkAcceleration, 0f)

        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            accelerationVec.add(walkAcceleration, 0f)

        accelerationVec.add(0f, -gravity)

        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt)

        if (!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
            val decelerationAmount = walkDeceleration * dt

            var walkDirection = 0f

            if (velocityVec.x > 0)
                walkDirection = 1f
            else
                walkDirection = -1f

            var walkSpeed = abs(velocityVec.x)

            if (walkSpeed < 0)
                walkSpeed = 0f
            velocityVec.x = walkSpeed * walkDirection

            velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed)
            velocityVec.y = MathUtils.clamp(velocityVec.y, -maxVerticalSpeed, maxVerticalSpeed)

            moveBy(velocityVec.x * dt, velocityVec.y * dt)
            accelerationVec.set(0f, 0f)
            belowSensor.setPosition(x + 4f, y - 8f)

            alignCamera()
            boundToWorld()

            if (this.isOnSolid()) {
                belowSensor.color = Color.GREEN
                if (velocityVec.x == 0f)
                    setAnimation(stand)
                else
                    setAnimation(walk)
            } else {
                belowSensor.color = Color.GREEN
                setAnimation(jump)
            }

            if (velocityVec.x > 0) // face right
                setScale(1f)
            if (velocityVec.x < 0) // face left
                setScale(-1f)
        }
    }

    fun belowOverlaps(actor: BaseActor): Boolean { return belowSensor.overlaps(actor) }

    fun isOnSolid(): Boolean {
        for (actor in BaseActor.getList(stage, Solid::class.java.canonicalName)) {
            val solid = actor as Solid
            if (belowOverlaps(solid) && solid.enabled)
                return true
        }
        return false
    }

    fun jump() { velocityVec.y = jumpSpeed }
}
