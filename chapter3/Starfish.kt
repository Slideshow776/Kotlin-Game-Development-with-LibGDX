package chapter3

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

private var collected: Boolean = false

class Starfish(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/starfish.png")
        setBoundaryPolygon(8)

        // Actions
        val spin: Action = Actions.rotateBy(30.toFloat(), 1.toFloat())
        this.addAction(Actions.forever(spin))
    }

    fun isCollected() = collected

    fun collect() {
        collected = true
        clearActions()
        addAction(Actions.fadeOut(1f))
        addAction(Actions.after(Actions.removeActor()))
    }
}