package chapter2

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object LauncherBeta {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(
            StarfishCollectorBeta(),
            "Starfish Collector",
            800,
            600
        )
    }
}