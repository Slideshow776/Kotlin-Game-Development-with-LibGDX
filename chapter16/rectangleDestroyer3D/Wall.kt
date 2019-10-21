package chapter16.rectangleDestroyer3D

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage

class Wall(x: Float, y: Float, width: Float, height: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/white-square.png")
        setSize(width, height)
        color = Color.GRAY
        setBoundaryRectangle()
    }
}
