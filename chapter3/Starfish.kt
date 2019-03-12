package chapter3

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

private var x: Float? = null
private var y: Float? = null
private var s: Stage? = null

class Starfish(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/starfish.png")

        val spin: Action = Actions.rotateBy(30.toFloat(), 1.toFloat())
        this.addAction(Actions.forever(spin))
    }
}