package chapter06.rhythmTapper

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class FallingBox(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/box.png")
        setScale(.75f, .75f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }

    fun flashOut() {
        val duration = .25f
        val flashOut = Actions.parallel(
            Actions.scaleTo(1.5f, 1.5f, duration),
            Actions.color(Color.WHITE, duration),
            Actions.fadeOut(duration)
        )
        addAction(flashOut)
        addAction(Actions.after(Actions.removeActor()))
    }
}
