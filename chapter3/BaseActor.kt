package chapter3

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage

/**
*   Extend functionality of the LibGDX Actor class.
*/
class BaseActor(x: Float, y: Float, s: Stage) : Actor() {
    init {
        setPosition(x, y)
        s.addActor(this)
    }
}

