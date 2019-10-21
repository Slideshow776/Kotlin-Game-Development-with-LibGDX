package chapter16.rectangleDestroyer3D

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(RectangleDestroyer(), "Rectangle Destroyer", 832, 640)
    }
}
