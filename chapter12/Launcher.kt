package chapter12

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

object Launcher {
    @JvmStatic
    fun main (args: Array<String>) {
        LwjglApplication(
            TreasureQuestGame(),
            "Treasure Quest",
            800,
            600
        )
    }
}
