package chapter7

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen: BaseScreen() {

    private lateinit var sky0: Sky
    private lateinit var sky1: Sky
    private lateinit var ground0: Ground
    private lateinit var ground1: Ground

    private lateinit var titleLabel: Label
    private lateinit var highscoreLabel: Label

    private lateinit var startButton: TextButton
    private lateinit var exitButton: TextButton

    override fun initialize() {
        sky0 = Sky(0f, 0f, mainStage)
        sky1 = Sky(800f, 0f, mainStage)
        ground0 = Ground(0f, 0f, mainStage)
        ground1 = Ground(800f, 0f, mainStage)

        titleLabel = Label("Plane Dodger", chapter7.BaseGame.labelStyle)
        titleLabel.setFontScale(1.2f)
        titleLabel.color = Color.PINK

        highscoreLabel = Label("Highscore: ${BaseGame.highscore}", chapter7.BaseGame.labelStyle)

        startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener {e: Event ->
            if (isTouchDownEvent(e)) {
                fadeOutAndDisable(.5f)
                sky0.addAction(Actions.after(Actions.run { BaseGame.setActiveScreen(LevelScreen()) }))
            }
            false
        }
        exitButton = TextButton("Exit", BaseGame.textButtonStyle)
        exitButton.addListener {e: Event ->
            if (isTouchDownEvent(e)) {
                fadeOutAndDisable(.5f)
                sky0.addAction(Actions.after(Actions.run { Gdx.app.exit() }))
            }
            false
        }

        // scene graph
        /*uiTable.debug = true*/
        uiTable.add(titleLabel).colspan(2)
        uiTable.row()
        uiTable.add(highscoreLabel).colspan(2)
        uiTable.row()
        uiTable.add(startButton).width(150f).padLeft(25f)
        uiTable.row()
        uiTable.add(exitButton).width(150f).padLeft(25f)

        fadeIn(.5f)
    }

    override fun update(dt: Float) {

    }

    private fun fadeOutAndDisable(duration: Float) {
        startButton.isDisabled = true
        exitButton.isDisabled = true

        sky0.addAction(Actions.fadeOut(duration))
        sky1.addAction(Actions.fadeOut(duration))
        ground0.addAction(Actions.fadeOut(duration))
        ground1.addAction(Actions.fadeOut(duration))
        titleLabel.addAction(Actions.fadeOut(duration))
        highscoreLabel.addAction(Actions.fadeOut(duration))
        startButton.addAction(Actions.fadeOut(duration))
        exitButton.addAction(Actions.fadeOut(duration))
    }

    private fun fadeIn(duration: Float) {
        sky0.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
        sky1.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
        ground0.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
        ground1.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
        titleLabel.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
        highscoreLabel.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
        startButton.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
        exitButton.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
    }
}
