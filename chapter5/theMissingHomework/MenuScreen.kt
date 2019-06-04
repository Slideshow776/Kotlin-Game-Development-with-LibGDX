package chapter5.theMissingHomework

import chapter5.theMissingHomework.StoryScreen
import chapter5.theMissingHomework.BaseScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen : BaseScreen() {
    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/notebook.jpg")
        background.setSize(800f, 600f)

        val title = BaseActor(0f, 0f, mainStage)
        title.loadTexture("assets/missing-homework.png")

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener { e: Event ->
            val ie = e as InputEvent
            if (ie.type == Type.touchDown)
                BaseGame.setActiveScreen(StoryScreen())
            false
        }

        val quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        quitButton.addListener { e: Event ->
            val ie = e as InputEvent
            if (ie.type == Type.touchDown)
                Gdx.app.exit()
            false
        }

        uiTable.add(title).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton)
    }

    override fun update(dt: Float) {}

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Keys.ENTER))
            BaseGame.setActiveScreen(StoryScreen())
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()
        return false
    }
}
