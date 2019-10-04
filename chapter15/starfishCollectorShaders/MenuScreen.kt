package chapter15.starfishCollectorShaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen : BaseScreen() {
    private lateinit var backgroundMusic: Music
    private lateinit var ocean: WaterBackground

    override fun initialize() {
        ocean = WaterBackground(0f, 0f, "assets/water.jpg", mainStage)
        ocean.setSize(800f, 600f)

        val background = VignetteBackground(0f, 0f, "assets/opaqueWhite.png", mainStage)
        background.setSize(800f, 600f)

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
            if (isTouchDownEvent(e)) {
                val transition = Transition(0f, 0f, "assets/transition2.png", uiStage)
                transition.enabled = true
                transition.wayIn = true
                ocean.addAction(
                    Actions.sequence(
                        Actions.delay(transition.duration, Actions.run {
                            transition.remove()
                            dispose()
                            BaseGame.setActiveScreen(StoryScreen())
                        })
                    )
                )
            }
            false
        }

        val quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        /*quitButton.setPosition(500f, 150f)
        uiStage.addActor(quitButton)*/
        quitButton.addListener { e: Event ->
            if (isTouchDownEvent(e)) {
                dispose()
                Gdx.app.exit()
            }
            false
        }

        uiTable.add(title).colspan(2)
        uiTable.row()
        uiTable.add(startButton)
        uiTable.add(quitButton)

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/fantasy-orchestra.wav"))
        backgroundMusic.isLooping = true
        backgroundMusic.play()

        val transition = Transition(0f, 0f, "assets/transition2.png", uiStage)
        transition.enabled = true
        transition.wayIn = false
    }

    override fun keyDown(keyCode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
            val transition = Transition(0f, 0f, "assets/transition2.png", uiStage)
            transition.enabled = true
            transition.wayIn = true
            ocean.addAction(
                Actions.sequence(
                    Actions.delay(transition.duration, Actions.run {
                        transition.remove()
                        dispose()
                        BaseGame.setActiveScreen(StoryScreen())
                    })
                )
            )
        }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            dispose()
            Gdx.app.exit()
        }
        return false
    }

    override fun update(dt: Float) {
        /*if (Gdx.input.isKeyPressed(Keys.S)) {
            BaseGame.setActiveScreen(StoryScreen())
        }*/
    }

    override fun dispose() {
        super.dispose()
        backgroundMusic.dispose()
    }
}