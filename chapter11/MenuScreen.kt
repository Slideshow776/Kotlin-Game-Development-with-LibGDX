package chapter11

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class MenuScreen : BaseScreen() {

    private lateinit var backgroundMusic: Music
    private lateinit var title: Label

    override fun initialize() {
        Parallax(1600f, 0f, mainStage, "assets/b5.png", 2f)
        Parallax(0f, 0f, mainStage, "assets/b5.png", 2f)
        Parallax(1600f, 0f, mainStage, "assets/b4.png", 5f)
        Parallax(0f, 0f, mainStage, "assets/b4.png", 5f)
        Parallax(1600f, 0f, mainStage, "assets/b3.png", 10f)
        Parallax(0f, 0f, mainStage, "assets/b3.png", 10f)
        Parallax(1600f, 0f, mainStage, "assets/b2.png", 20f)
        Parallax(0f, 0f, mainStage, "assets/b2.png", 20f)
        Parallax(1600f, 0f, mainStage, "assets/b1.png", 40f)
        Parallax(0f, 0f, mainStage, "assets/b1.png", 40f)
        Parallax(0f, 0f, mainStage, "assets/b0.png", 60f)
        Parallax(1600f, 0f, mainStage, "assets/b0.png", 60f)

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener { e: Event ->
            val ie = e as InputEvent
            if (ie.type == Type.touchDown) {
                dispose()
                BaseGame.setActiveScreen(LevelScreen())
            }
            false
        }

        val quitButton = TextButton("Quit", BaseGame.textButtonStyle)
        quitButton.addListener { e: Event ->
            val ie = e as InputEvent
            if (ie.type == Type.touchDown) {
                dispose()
                Gdx.app.exit()
            }
            false
        }

        title = Label("Jack the Koala!", BaseGame.labelStyle)
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

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/cartoon-game-theme-loop.wav"))
        backgroundMusic.isLooping = true
        backgroundMusic.play()

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

    override fun update(dt: Float) {
    }

    override fun dispose() {
        super.dispose()
        backgroundMusic.dispose()
    }
}
