package chapter14

import com.badlogic.gdx.scenes.scene2d.Stage

class Wall(x: Float, y: Float, width: Float, height: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/square.jpg")
        setSize(width, height)
        setBoundaryRectangle()
    }
}
