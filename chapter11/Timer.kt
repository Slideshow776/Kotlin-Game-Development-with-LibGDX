package chapter11

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Timer(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/items/timer.png")
        val pulse = Actions.sequence(
            Actions.scaleTo(1.1f, 1.1f, .5f),
            Actions.scaleTo(1f, 1f, .5f)
        )
        addAction(Actions.forever(pulse))
    }
}
