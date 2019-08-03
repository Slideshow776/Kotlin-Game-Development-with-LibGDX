package chapter11

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Key(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/items/key.png")

        rotateBy(10f)
        val tilt = Actions.sequence(
            Actions.rotateBy(-20f, .5f),
            Actions.rotateBy(20f, .5f)
        )
        addAction(Actions.forever(tilt))
    }
}
