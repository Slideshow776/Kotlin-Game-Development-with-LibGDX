package chapter5

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Starfish(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private var collected: Boolean = false

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
