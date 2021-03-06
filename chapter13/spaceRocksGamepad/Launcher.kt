package chapter13.spaceRocksGamepad

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        val myGame = SpaceGame()
        LwjglApplication(myGame, "Space Rocks", 800, 600)
    }
}