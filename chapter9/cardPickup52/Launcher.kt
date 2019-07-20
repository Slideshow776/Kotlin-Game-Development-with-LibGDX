package chapter9.cardPickup52

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(PickupGame(), "52 Card Pickup", 800, 600)
    }
}
