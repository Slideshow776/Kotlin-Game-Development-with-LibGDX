package chapter6.rythmTapper

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object RythmLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val myGame = RythmGame()
        val launcher = LwjglApplication(myGame, "Recorder", 800, 600)
    }
}
