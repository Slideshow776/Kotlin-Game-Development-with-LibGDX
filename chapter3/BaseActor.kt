package chapter3

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.graphics.g2d.Batch

/**
*   Extend functionality of the LibGDX Actor class.
*/
class BaseActor(x: Float, y: Float, s: Stage) : Actor() {

    private lateinit var animation: Animation<TextureRegion>
    private var elapsedTime: Float = 0.toFloat()
    private var animationPaused: Boolean = false

    init {
        setPosition(x, y)
        s.addActor(this)
    }

    fun setAnimation(anim: Animation<TextureRegion>) {
        animation = anim
        val tr: TextureRegion = animation.getKeyFrame(0.toFloat())
        val w: Float = tr.regionWidth.toFloat()
        val h: Float = tr.regionHeight.toFloat()
        setSize(w, h)
        setOrigin(w/2, h/2)
    }

    fun setAnimationPaused(pause: Boolean) {
        animationPaused = pause
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (!animationPaused)
            elapsedTime += dt
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        //  apply color tint effect
        val c: Color = color
        batch.setColor(c.r, c.g, c.b, c.a)

        if (animation != null && isVisible) {
            batch.draw(
                animation.getKeyFrame(elapsedTime),
                x,
                y,
                originX,
                originY,
                width,
                height,
                scaleX,
                scaleY,
                rotation
            )
        }

    }
}

