package chapter10.rectangleDestroyer

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage

class Wall(x: Float, y: Float, width: Float, height: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        println("$x, $y, $width, $height, $s")
        loadTexture("assets/white-square.png")
        setSize(width, height)
        color = Color.GRAY
        setBoundaryRectangle()
    }
}
