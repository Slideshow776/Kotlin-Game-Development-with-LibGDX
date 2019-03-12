package chapter3

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.Game

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        val myGame: Game = StarfishCollector()
        LwjglApplication(
            myGame,
            "Starfish Collector",
            800,
            600
        )
    }
}