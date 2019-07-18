package chapter9.jigsawPuzzle

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(JigsawPuzzleGame(), "Jigsaw Puzzle Game", 800, 600)
    }
}
