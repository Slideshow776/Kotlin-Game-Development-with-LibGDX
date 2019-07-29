package chapter11

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(JumpingJackGame(), "Jumping Jack", 800, 640)
    }
}
