package chapter07

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(
            PlaneDodgerGame(),
            "Plane Dodger",
            800,
            600
        )
    }
}
