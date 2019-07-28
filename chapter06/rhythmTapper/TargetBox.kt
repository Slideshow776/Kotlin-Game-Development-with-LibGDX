package chapter06.rhythmTapper

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align

class TargetBox(x: Float, y: Float, s: Stage, letter: String, color: Color): BaseActor(x, y, s) {
    init {
        loadTexture("assets/box.png")
        setSize(64f, 64f)

        // add a centered label containing letter with given color
        val letterLabel = Label(letter, BaseGame.labelStyle)
        letterLabel.setSize(64f, 64f)
        letterLabel.setAlignment(Align.center)
        letterLabel.color = color
        this.addActor(letterLabel)
    }

    fun pulse() {
        val pulse = Actions.sequence(
            Actions.scaleTo(1.2f, 1.2f, .05f),
            Actions.scaleTo(1.0f, 1.0f, .05f)
        )
        addAction(pulse)
    }
}
