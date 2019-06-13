package chapter6.rhythmTapper

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object RhythmLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val myGame = RhythmGame()
        val launcher = LwjglApplication(myGame, "Recorder", 800, 600)
    }
}
