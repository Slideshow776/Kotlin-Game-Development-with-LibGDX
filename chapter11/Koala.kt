package chapter11

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
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

    private var health = 3
    private var invincible = false
    private var invincibleTimer = 0f

    private var hurtSound: Sound

    init {
        stand = loadTexture("assets/koala/stand.png")

        val walkFileNames: Array<String> = Array()
        walkFileNames.add("assets/koala/walk-1.png")
        walkFileNames.add("assets/koala/walk-2.png")
        walkFileNames.add("assets/koala/walk-3.png")
        walkFileNames.add("assets/koala/walk-2.png")
        walk = loadAnimationFromFiles(walkFileNames, .2f, true)

        jump = loadTexture("assets/koala/jump.png")
        setBoundaryPolygon(8)

        belowSensor = BaseActor(0f, 0f, s)
        belowSensor.loadTexture("assets/white.png")
        belowSensor.setSize(this.width - 8f, 8f)
        belowSensor.setBoundaryRectangle()
        belowSensor.isVisible = false

        hurtSound = Gdx.audio.newSound(Gdx.files.internal("assets/hurt.wav"))
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (Gdx.input.isKeyPressed(Keys.A))
            accelerationVec.add(-walkAcceleration, 0f)

        if (Gdx.input.isKeyPressed(Keys.D))
            accelerationVec.add(walkAcceleration, 0f)

        if (!Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A)) {
            val decelerationAmount = walkDeceleration * dt

            val walkDirection: Float

            if (velocityVec.x > 0)
                walkDirection = 1f
            else
                walkDirection = -1f

            var walkSpeed = abs(velocityVec.x)
            walkSpeed -= decelerationAmount

            if (walkSpeed < 0)
                walkSpeed = 0f
            velocityVec.x = walkSpeed * walkDirection
        }

        accelerationVec.add(0f, -gravity)

        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt)

        velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed)

        moveBy(velocityVec.x * dt, velocityVec.y * dt)

        accelerationVec.set(0f, 0f)

        belowSensor.setPosition(x + 4, y - 8)

        if (this.isOnSolid()) {
            belowSensor.color = Color.GREEN
            if (velocityVec.x == 0f)
                setAnimation(stand)
            else
                setAnimation(walk)
        } else {
            belowSensor.color = Color.RED
            setAnimation(jump)
        }

        if (velocityVec.x > 0) // face right
            scaleX = 1f
        if (velocityVec.x < 0) // face left
            scaleX = -1f

        alignCamera(lerp=.1f)
        boundToWorld()

        if (invincible && invincibleTimer < 1.5f) {
            invincibleTimer += dt
        } else {
            invincibleTimer = 0f
            invincible = false
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
    fun isFalling():Boolean = velocityVec.y < 0f
    fun spring() { velocityVec.y = 1.5f * jumpSpeed }
    fun isJumping() = velocityVec.y > 0f

    fun hit(table: Table, list: ArrayList<BaseActor>): Int {
        if (!invincible) {
            if (health > 1) {
                addAction(
                    Actions.sequence(
                        Actions.color(Color.RED, .125f),
                        Actions.color(Color.WHITE, .125f),
                        Actions.color(Color.RED, .125f),
                        Actions.color(Color.WHITE, .125f),
                        Actions.color(Color.RED, .125f),
                        Actions.color(Color.WHITE, .125f)
                    ))
            }
            hurtSound.play()
            table.removeActor(list.removeAt(0))
            health--
            invincible = true
        }
        return health
    }

    fun getHealth(): Int { return health }
}
