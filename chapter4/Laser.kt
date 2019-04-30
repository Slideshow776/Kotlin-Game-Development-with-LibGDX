package chapter4

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Laser(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/laser.png")

        addAction(Actions.delay(1f))
        addAction(Actions.after(Actions.fadeOut(.5f)))
        addAction(Actions.after(Actions.removeActor()))

        setSpeed(400f)
        setMaxSpeed(400f)
        setDeceleration(0f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
        wrapArpundWorld()
    }
}