package chapter02

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object LauncherAlpha {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(
            StarfishCollectorAlpha(),
            "Starfish Collector",
            800,
            600
        )
    }
}