package chapter16.starfishCollector3D

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val myGame = StarfishCollector3DGame()
        LwjglApplication(myGame, "Starfish Collector 3D", 800, 600)
    }
}
