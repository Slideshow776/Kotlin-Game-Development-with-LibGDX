package chapter04

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Warp(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/warp.png", 4, 8, .05f, true)
        setScale(Constants.scale, Constants.scale)

        addAction(Actions.delay(1f))
        addAction(Actions.after(Actions.fadeOut(.5f)))
        addAction(Actions.after(Actions.removeActor()))
    }
}
