package chapter4

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class MenuScreen : BaseScreen() {

    override fun initialize() {
        var space = BaseActor(0f, 0f, mainStage)
        space.loadTexture("assets/space.png")
        space.setSize(800f, 600f)

        var start = BaseActor(0f, 0f, mainStage)
        start.loadTexture("assets/message-start.png")
        start.centerAtPosition(400f, 300f)
        start.setOpacity(0f)
        start.addAction(Actions.fadeIn(1f))
    }

    override fun update(dt: Float) {}

    // Override default InputProcessor method
    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.S) {
            BaseGame.setActiveScreen(LevelScreen())
        }
        return false
    }
}
