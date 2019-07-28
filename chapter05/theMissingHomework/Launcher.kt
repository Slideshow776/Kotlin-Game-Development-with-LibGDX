package chapter05.theMissingHomework

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        val game = HomeworkGame()
        LwjglApplication(game, "The Missing Homework", 800, 600)
    }
}
