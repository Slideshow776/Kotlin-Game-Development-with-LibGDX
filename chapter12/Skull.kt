package chapter12

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import kotlin.math.atan2

class Skull(x: Float, y: Float, s: Stage, private val hero: Hero) : BaseActor(x, y, s) {
    private var idle: Animation<TextureRegion>
    private var charging: Animation<TextureRegion>
    private var skullScreamAudio: Sound
    private var skullScreamed: Boolean = false

    init {
        val fileName = "assets/skull.png"
        val rows = 2
        val cols = 1
        val texture = Texture(Gdx.files.internal(fileName), true)
        val frameWidth = texture.width / cols
        val frameHeight = texture.height / rows
        val frameDuration = .2f

        val temp = TextureRegion.split(texture, frameWidth, frameHeight)
        val textureArray = Array<TextureRegion>()
        for (c in 0 until cols) {
            textureArray.add(temp[0][c])
        }
        idle = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        textureArray.clear()
        for (c in 0 until cols) {
            textureArray.add(temp[1][c])
        }
        charging = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        setAnimation(idle)
        setSize(50f, 50f) // setSize() needs to be called after setAnimation()

        // setSize(48f, 48f)
        setBoundaryPolygon(6)

        setSpeed(MathUtils.random(50f, 80f))
        setMotionAngle(MathUtils.random(0f, 360f))

        skullScreamAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/skullScream.wav"))
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
        boundToWorld()

        if (this.isWithinDistance(200f, hero)) { // distance unit is pixels
            if (!skullScreamed) {
                skullScreamAudio.play(.5f)
                skullScreamed = true
            }
            setAnimation(charging)
            setSize(50f, 50f)
            val degrees = (atan2(
                -(this.y - hero.y).toDouble(),
                -(this.x - hero.x).toDouble()
            ) * 180.0 / Math.PI).toFloat()
            setMotionAngle(degrees)
        } else {
            skullScreamed = false
            setAnimation(idle)
            setSize(50f, 50f)
            if (MathUtils.random(1, 120) == 1) {
                setMotionAngle(MathUtils.random(0f, 360f))
            }
        }
    }
}
