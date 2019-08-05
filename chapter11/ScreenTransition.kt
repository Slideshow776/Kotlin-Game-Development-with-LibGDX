package chapter11

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class ScreenTransition(x: Float, y: Float, s: Stage): BaseActor(x, y, s) {
    init {
        loadTexture("assets/overlay.png")
        width = 800f
        height = 640f
        addAction(
            Actions.sequence(
                Actions.delay(1f),
                Actions.moveBy(-800f, 0f, .75f, Interpolation.sine),
                Actions.removeActor()
            ))
    }
}
