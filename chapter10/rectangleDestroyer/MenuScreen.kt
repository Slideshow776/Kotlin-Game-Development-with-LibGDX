package chapter10.rectangleDestroyer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen : BaseScreen() {
    override fun initialize() {
        // background
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/space.png")

        // user interface
        val titleLabel = Label("Rectangle Destroyer", BaseGame.labelStyle)

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener {e: Event ->
            if (isTouchDownEvent(e)) {
                background.addAction(Actions.fadeOut(1f))
                uiTable.addAction(Actions.fadeOut(1f))
                background.addAction(Actions.after(
                    Actions.run { BaseGame.setActiveScreen(LevelScreen()) }
                ))
            }
            false
        }

        val exitButton = TextButton("Quit", BaseGame.textButtonStyle)
        exitButton.addListener {e: Event ->
            if (isTouchDownEvent(e)) {
                background.addAction(Actions.fadeOut(1f))
                uiTable.addAction(Actions.fadeOut(1f))
                background.addAction(Actions.after(
                    Actions.run { Gdx.app.exit() }
                ))
            }
            false
        }

        // scene graph
        uiTable.add(titleLabel).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(exitButton)
    }

    override fun update(dt: Float) {}

}