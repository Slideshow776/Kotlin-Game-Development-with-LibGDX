package chapter5

import chapter5.starfishCollector.BaseGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen: BaseScreen() {
    override fun initialize() {
        val ocean = BaseActor(0f, 0f, mainStage)
        ocean.loadTexture("assets/water.jpg")
        ocean.setSize(800f, 600f)

        val title = BaseActor(0f, 0f, mainStage)
        title.loadTexture("assets/starfish-collector.png")
        /*title.centerAtPosition(400f, 300f)
        title.moveBy(0f, 100f)*/

        /*var start = BaseActor(0f, 0f, mainStage)
        start.loadTexture("assets/message-start.png")
        start.centerAtPosition(400f, 300f)
        start.moveBy(0f, -100f)*/

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        /*startButton.setPosition(150f, 150f)
        uiStage.addActor(startButton)*/
        startButton.addListener { e: Event ->
            val ie = e as InputEvent
            if (ie.type == Type.touchDown)
                BaseGame.setActiveScreen(LevelScreen())
            false
        }

        val quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        /*quitButton.setPosition(500f, 150f)
        uiStage.addActor(quitButton)*/
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

    override fun keyDown(keyCode: Int) : Boolean {
        if (Gdx.input.isKeyPressed(Keys.ENTER))
            BaseGame.setActiveScreen(LevelScreen())
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit()
        return false
    }

    override fun update(dt: Float) {
        if (Gdx.input.isKeyPressed(Keys.S)) {
            BaseGame.setActiveScreen(LevelScreen())
        }
    }
}