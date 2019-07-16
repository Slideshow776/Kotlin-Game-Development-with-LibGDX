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

    private lateinit var overlay: BaseActor

    override fun initialize() {
        overlay = BaseActor(0f, 0f, uiStage)
        overlay.loadTexture("assets/overlay.png")
        overlay.width = 800f
        overlay.height = 600f

        /*
        sky0 = Sky(0f, 0f, mainStage)
        sky1 = Sky(800f, 0f, mainStage)
        ground0 = Ground(0f, 0f, mainStage)
        ground1 = Ground(800f, 0f, mainStage)
        */
        Parallax(800f, 0f, mainStage, "assets/clouds0.png", 10f)
        Parallax(0f, 0f, mainStage, "assets/clouds0.png", 10f)
        Parallax(800f, 0f, mainStage, "assets/clouds1.png", 25f)
        Parallax(0f, 0f, mainStage, "assets/clouds1.png", 25f)
        Parallax(800f, 0f, mainStage, "assets/mountains0.png", 35f)
        Parallax(0f, 0f, mainStage, "assets/mountains0.png", 35f)
        Parallax(800f, 0f, mainStage, "assets/mountains1.png", 45f)
        Parallax(0f, 0f, mainStage, "assets/mountains1.png", 45f)
        Ground(0f, 0f, mainStage)
        Ground(800f, 0f, mainStage)

        titleLabel = Label("Plane Dodger", chapter7.BaseGame.labelStyle)
        titleLabel.setFontScale(1.2f)
        titleLabel.color = Color.PINK

        highscoreLabel = Label("Highscore: ${BaseGame.highscore}", chapter7.BaseGame.labelStyle)

        startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener {e: Event ->
            if (isTouchDownEvent(e)) {
                fadeOutAndDisable(.5f)
                overlay.addAction(Actions.after(Actions.run { BaseGame.setActiveScreen(LevelScreen()) }))
            }
            false
        }
        exitButton = TextButton("Exit", BaseGame.textButtonStyle)
        exitButton.addListener {e: Event ->
            if (isTouchDownEvent(e)) {
                fadeOutAndDisable(.5f)
                overlay.addAction(Actions.after(Actions.run { Gdx.app.exit() }))
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

    override fun update(dt: Float) {}

    private fun fadeOutAndDisable(duration: Float) {
        startButton.isDisabled = true
        exitButton.isDisabled = true

        overlay.isVisible = true
        overlay.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(duration)))
    }

    private fun fadeIn(duration: Float) {
        overlay.addAction(Actions.fadeOut(duration))
        overlay.addAction(Actions.after(Actions.run { overlay.isVisible = false }))
    }
}
