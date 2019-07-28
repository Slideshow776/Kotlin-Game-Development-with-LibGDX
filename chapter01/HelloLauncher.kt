package chapter01

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object HelloLauncher {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(HelloWorldImage())
    }
}