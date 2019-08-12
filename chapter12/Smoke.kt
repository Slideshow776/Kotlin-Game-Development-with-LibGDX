package chapter12

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Smoke(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/smoke.png")
        addAction(Actions.fadeOut(.5f))
        addAction(Actions.after(Actions.removeActor()))
    }
}
