package chapter13.spaceRocksTouchscreen

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class Spaceship(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    private var thrusters: Thrusters
    private var shield: Shield
    var shieldPower: Int
    private val s: Stage
    private var thrustersSound: Music

    init {
        this.s = s

        loadTexture("assets/spaceship.png")
        setScale(Constants.scale, Constants.scale)
        setBoundaryPolygon(8)

        setAcceleration(220f)
        setMaxSpeed(200f)
        setDeceleration(1f)

        thrusters = Thrusters(0f, 0f, s)
        addActor(thrusters)
        thrusters.setPosition(-thrusters.width, height / 2 - thrusters.height / 2)

        shield = Shield(0f, 0f, s)
        addActor(shield)
        shield.centerAtPosition(width / 2, height / 2)
        shieldPower = 100

        thrustersSound = Gdx.audio.newMusic(Gdx.files.internal("assets/thrusters.wav"))
    }

    override fun act(dt: Float) {
        super.act(dt)

        val degreesPerSecond = 140f // rotation speed
        if(Gdx.input.isKeyPressed((Keys.A)))
            rotateBy(degreesPerSecond * dt)
        if(Gdx.input.isKeyPressed((Keys.D)))
            rotateBy(-degreesPerSecond * dt)
        if(Gdx.input.isKeyPressed((Keys.W))) {
            accelerateAtAngle(rotation)
            thrusters.isVisible = true
            playConsecutiveAudio(thrustersSound)
        } else {
            thrusters.isVisible = false
        }

        applyPhysics(dt)
        wrapAroundWorld()

        shield.setOpacity(shieldPower / 200f)
        if (shieldPower <= 0)
            shield.isVisible = false
    }

    fun rotate(direction: Vector2, dt: Float) {
        if (direction.len() > .25) { // dead zone
            val degreesPerSecond = 140f // rotation speed
            if (direction.angle() < 90 || (direction.angle() > 270 && direction.angle() <= 360))
                rotateBy(-degreesPerSecond * dt)
            else if (direction.angle() >= 90 && direction.angle() <= 270)
                rotateBy(degreesPerSecond * dt)
        }
    }

    fun move(shouldMove: Boolean) {
        if (shouldMove) { // dead zone
            playConsecutiveAudio(thrustersSound)
            accelerateAtAngle(rotation)
            thrusters.isVisible = true
        } else {
            thrusters.isVisible = false
        }
    }

    fun warp() {
        if (stage == null)
            return
        var warp1 = Warp(0f, 0f, this.stage)
        warp1.centerAtActor(this)
        setPosition(MathUtils.random(800f), MathUtils.random(600f))
        var warp2 = Warp(0f, 0f, this.stage)
        warp2.centerAtActor(this)
    }

    fun shoot() {
        if (stage == null)
            return
        if (count(s, Laser::class.java.canonicalName) >= 4)
            return

        var laser = Laser(0f, 0f, this.stage)
        laser.centerAtActor(this)
        laser.rotation = this.rotation
        laser.setMotionAngle(this.rotation)
    }

    private fun playConsecutiveAudio(music: Music) {
        if (!music.isPlaying) // only music have an isPlaying method
            music.play()
    }
}
