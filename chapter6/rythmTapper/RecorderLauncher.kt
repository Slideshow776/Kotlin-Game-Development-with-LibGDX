package chapter6.rythmTapper

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object RecorderLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val myGame = RecorderGame()
        val launcher = LwjglApplication(myGame, "Recorder", 800, 600)
    }
}