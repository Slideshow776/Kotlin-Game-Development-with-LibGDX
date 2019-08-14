package chapter12

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen : BaseScreen() {
    private lateinit var title: Label

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/overlay.png")
        background.width = Gdx.graphics.width.toFloat()
        background.height = Gdx.graphics.height.toFloat()
        background.color = Color.PINK

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener { e: Event ->
            if (isTouchDownEvent(e)) {
                dispose()
                BaseGame.setActiveScreen(LevelScreen())
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

        title = Label("Treasure Quest!", BaseGame.labelStyle)
        title.color = Color.PURPLE
        val group = Group()
        group.addActor(title)
        group.addAction(Actions.sequence(
            Actions.scaleTo(0f, 0f, 0f),
            Actions.delay(2f),
            Actions.scaleTo(1f, 1f, 2f, Interpolation.elasticOut)
        ));
        title.setPosition( group.width / 2f - title.width / 2f, group.height)

        // uiTable.debug()
        uiTable.add(group).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton)

        ScreenTransition(0f, 0f, uiStage)
    }

    override fun keyDown(keyCode: Int) : Boolean {
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
            dispose()
            BaseGame.setActiveScreen(LevelScreen())
        }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            dispose()
            Gdx.app.exit()
        }
        return false
    }

    override fun update(dt: Float) {}
}
