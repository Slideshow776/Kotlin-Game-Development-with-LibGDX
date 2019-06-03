package chapter5.theMissingHomework

import chapter5.HomeworkGame
import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object LauncherHomework {
    @JvmStatic
    fun main(args: Array<String>) {
        val game = HomeworkGame()
        LwjglApplication(game, "The Missing Homework", 800, 600)
    }
}
