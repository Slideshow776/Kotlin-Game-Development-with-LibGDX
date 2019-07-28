package chapter07

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Array

class Plane(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    private var health = 3
    private var invincible = false
    private var invincibleTimer = 0f

    private lateinit var explosionSound: Sound

    init {
        val fileNames: Array<String> = Array()
        fileNames.add("assets/planeGreen0.png")
        fileNames.add("assets/planeGreen1.png")
        fileNames.add("assets/planeGreen2.png")
        fileNames.add("assets/planeGreen1.png")
        loadAnimationFromFiles(fileNames, .1f, true)

        setMaxSpeed(800f)
        setBoundaryPolygon(8)

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/explosion.wav"))
    }

    override fun act(dt: Float) {
        super.act(dt)

        // simulate force of gravity
        setAcceleration(800f)
        accelerateAtAngle(270f)
        applyPhysics(dt)

        // stop plane from passing through the ground
        for (ground: BaseActor in BaseActor.getList(this.stage, Ground::class.java.canonicalName)) {
            if (this.overlaps(ground)) {
                setSpeed(0f)
                preventOverlap(ground)
            }
        }

        // stop plane from moving past the top of the screen
        if (y + height > getWorldBounds().height) {
            setSpeed(0f)
            boundToWorld()
        }
        if (invincible && invincibleTimer < 1.5f) {
            invincibleTimer += dt
        } else {
            invincibleTimer = 0f
            invincible = false
        }
    }

    fun hit(): Int {
        if (!invincible) {
            if (health > 1) {
                explosionSound.play()
                addAction(Actions.sequence(
                    Actions.color(Color.RED, .125f),
                    Actions.color(Color.WHITE, .125f),
                    Actions.color(Color.RED, .125f),
                    Actions.color(Color.WHITE, .125f),
                    Actions.color(Color.RED, .125f),
                    Actions.color(Color.WHITE, .125f)
                ))
            }
            health--
            invincible = true
        }
        return health
    }

    fun getHealth(): Int { return health }

    fun boost() {
        setSpeed(300f)
        setMotionAngle(90f)
        val exhaust = Exhaust(0f, 0f, this.stage)
        exhaust.centerAtPosition(x+20, y+20)
    }
}
