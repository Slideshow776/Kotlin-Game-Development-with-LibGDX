package chapter14

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color

class LevelScreen : BaseScreen() {
    lateinit var maze: Maze

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/white.png")
        background.color = Color.GRAY
        background.setSize(768f, 700f)

        maze = Maze(mainStage)
    }

    override fun update(dt: Float) {}

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.R) {
            dispose()
            BaseGame.setActiveScreen(LevelScreen())
        }
        return false
    }
}
