package chapter12

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array

class Hero(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private lateinit var north: Animation<TextureRegion>
    private lateinit var south: Animation<TextureRegion>
    private lateinit var east: Animation<TextureRegion>
    private lateinit var west: Animation<TextureRegion>
    var facingAngle: Float

    init {
        val fileName = "assets/hero.png"
        val rows = 4
        val cols = 4
        val texture = Texture(Gdx.files.internal(fileName), true)
        val framewidth = texture.width / cols
        val frameheight = texture.height / rows
        val frameDuration = .2f

        val temp = TextureRegion.split(texture, framewidth, frameheight)
        val textureArray = Array<TextureRegion>()
        for (c in 0 until cols) {
            textureArray.add(temp[0][c])
        }
        south = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        textureArray.clear()
        for (c in 0 until cols) {
            textureArray.add(temp[1][c])
        }
        west = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        textureArray.clear()
        for (c in 0 until cols) {
            textureArray.add(temp[2][c])
        }
        east = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        textureArray.clear()
        for (c in 0 until cols) {
            textureArray.add(temp[3][c])
        }
        north = Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG)

        setAnimation(south)
        facingAngle = 270f

        setBoundaryPolygon(8)
        setAcceleration(400f)
        setMaxSpeed(100f)
        setDeceleration(400f)

        alignCamera()
    }

    override fun act(dt: Float) {
        super.act(dt)
        // pause animation when character not moving
        if (getSpeed() == 0f)
            setAnimationPaused(true)
        else {
            setAnimationPaused(false)

            // set direction animation
            val angle = getMotionAngle()
            if (angle >= 45 && angle <= 135) {
                facingAngle = 90f
                setAnimation(north)
            } else if (angle > 135 && angle < 225) {
                facingAngle = 180f
                setAnimation(west)
            } else if (angle >= 225 && angle <= 315) {
                facingAngle = 270f
                setAnimation(south)
            } else {
                facingAngle = 0f
                setAnimation(east)
            }
        }

        //alignCamera(lerp =.1f)
        boundToWorld()
        applyPhysics(dt)
    }
}

