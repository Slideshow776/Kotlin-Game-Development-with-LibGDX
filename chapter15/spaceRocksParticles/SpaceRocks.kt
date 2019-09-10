package chapter15.spaceRocksParticles

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object SpaceRocks {
    @JvmStatic
    fun main(args: Array<String>) {
        val myGame = SpaceGame()
        LwjglApplication(myGame, "Space Rocks", 800, 600)
    }
}