package chapter11

import com.badlogic.gdx.scenes.scene2d.Stage

open class Solid(x: Float, y: Float, width: Float, height: Float, s: Stage): BaseActor(x, y, s) {
    var enabled = true

    init {
        setSize(width, height)
        setBoundaryRectangle()
    }
}
