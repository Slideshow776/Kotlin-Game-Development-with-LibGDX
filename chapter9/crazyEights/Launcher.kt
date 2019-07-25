package chapter9.crazyEights

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(CrazyEightsGame(), "Crazy Eights", 800, 600)
    }
}
