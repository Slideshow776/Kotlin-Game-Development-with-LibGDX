package chapter4

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class PowerUp(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/shields.png")
        setScale(Constants.scale, Constants.scale)

        setActions()
    }

    private fun setActions() {
        // scale pulse
        var scalePulse = Actions.sequence(
            Actions.scaleTo(1.05f, 1.05f, 1f),
            Actions.scaleTo(.95f, .95f, 1f)
        )
        addAction(Actions.forever(scalePulse))

        // color pulse
        var scaleColor = Actions.sequence(
            Actions.color(Color.WHITE, 1.5f),
            Actions.color(Color.GREEN, 1.5f)
        )
        addAction(Actions.forever(scaleColor))

        // fade in
        setOpacity(0f)
        addAction(Actions)

        // fade out
        addAction(Actions.delay(5f))
        addAction(Actions.after(Actions.fadeOut(.25f)))
        addAction(Actions.after(Actions.removeActor()))
    }
}