package chapter13.rectangleDestroyerTouchscreen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage

class Wall(x: Float, y: Float, s: Stage, width: Float, height: Float) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/white-square.png")
        setSize(width, height)
        color = Color.GRAY
        setBoundaryRectangle()
    }
}
