package chapter7

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Stars(x: Float, y: Float, s: Stage): BaseActor(x, y, s) {
    init {
        loadTexture("assets/star.png")

        val pulse = Actions.sequence(
            Actions.scaleTo(1.2f, 1.2f, .5f),
            Actions.scaleTo(1.0f, 1.0f, .5f)
        )
        addAction(Actions.forever(pulse))

        setSpeed(100f)
        setMotionAngle(180f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)

        // remove actor after moving past left edge of the screen
        if (x + width < 0)
            remove()
    }
}
