package chapter15.starfishCollectorShaders

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        // To start a LibGDX program, this method:
        // (1) creates an instance of the game
        // (2) creates a new application with game instance and window settings as argument
        val myGame = StarfishGame()
        LwjglApplication(myGame, "Starfish Collector", 800, 600)
    }
}