package chapter16.rectangleDestroyer3D

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(RectangleDestroyer3D(), "Rectangle Destroyer 3D", 832, 640)
    }
}
