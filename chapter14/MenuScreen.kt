package chapter14

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen() : BaseScreen() {
    private lateinit var title: Label

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/overlay.png")
        background.width = Gdx.graphics.width.toFloat()
        background.height = Gdx.graphics.height.toFloat()
        background.color = Color.GOLDENROD

        val overlay = BaseActor(0f, 0f, uiStage)
        overlay.loadTexture("assets/overlay.png")
        overlay.color = Color.BLACK
        overlay.width = Gdx.graphics.width.toFloat()
        overlay.height = Gdx.graphics.height.toFloat()
        overlay.addAction(
            Actions.sequence(
                Actions.fadeOut(1f),
                Actions.run { overlay.isVisible = false }
            )
        )
        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener { e: Event ->
            if (isTouchDownEvent(e)) {
                dispose()
                overlay.isVisible = true
                overlay.addAction(Actions.sequence(
                    Actions.fadeIn(1f),
                    Actions.run { BaseGame.setActiveScreen(LevelScreen()) }
                ))
            }
            false
        }

        val quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        quitButton.addListener { e: Event ->
            if (isTouchDownEvent(e)) {
                dispose()
                Gdx.app.exit()
            }
            false
        }

        title = Label("Maze Runman", BaseGame.labelStyle)
        title.color = Color.PURPLE
        title.addAction(Actions.fadeIn(2f))

        // uiTable.debug()
        uiTable.add(title).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton)
    }

    override fun update(dt: Float) {}
}