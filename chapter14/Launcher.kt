package chapter14

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

object Launcher {
    @JvmStatic
    fun main (args: Array<String>) {
        LwjglApplication(
            MazeGame(),
            "Maze Runman",
            768,
            700
        )
    }
}
