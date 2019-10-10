package chapter16.cubeDemo

import chapter16.project3D.demoCube
import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object DemoCubeLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        val myGame = demoCube()
        LwjglApplication(myGame, "Cube Demo", 800, 600)
    }
}
