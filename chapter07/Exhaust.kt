package chapter07

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Exhaust(x: Float, y: Float, s: Stage): BaseActor(x, y, s) {
    private lateinit var exhaust: Animation<TextureRegion>

    init {
        when(MathUtils.random(0, 2)) {
            0 -> exhaust = loadTexture("assets/exhaust0.png")
            1 -> exhaust = loadTexture("assets/exhaust1.png")
            2 -> exhaust = loadTexture("assets/exhaust2.png")
            else -> println("Exhaust.kt: Error! random number returned out of range")
        }

        addAction(Actions.fadeOut(1f))
        addAction(Actions.after(Actions.run { remove() }))

        setSpeed(100f)
        setMotionAngle(MathUtils.random(180f, 200f))
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }
}
