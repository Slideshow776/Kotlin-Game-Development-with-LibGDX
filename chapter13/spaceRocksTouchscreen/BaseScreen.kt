package chapter13.spaceRocksTouchscreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.Screen
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.utils.viewport.FitViewport

abstract class BaseScreen : Screen, InputProcessor {
    protected var mainStage: Stage
    protected var uiStage: Stage
    protected var uiTable: Table

    init {
        mainStage = Stage(FitViewport(800f, 600f))
        uiStage = Stage(FitViewport(800f, 600f))

        uiTable = Table()
        uiTable.setFillParent(true)
        uiStage.addActor(uiTable)

        initialize()
    }

    abstract fun initialize()
    abstract fun update(dt: Float)

    // Gameloop:
    // (1) process input (discrete handled by listener; continuous in update)
    // (2) update game logic
    // (3) render the graphics
    override fun render(dt: Float) {
        // act methods
        uiStage.act(dt)
        mainStage.act(dt)

        // defined by user
        update(dt)

        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // draw the graphics
        mainStage.draw()
        uiStage.draw()
    }

    override fun show() {
        val im: InputMultiplexer = Gdx.input.inputProcessor as InputMultiplexer
        im.addProcessor(this)
        im.addProcessor(uiStage)
        im.addProcessor(mainStage)
    }

    override fun hide() {
        val im: InputMultiplexer = Gdx.input.inputProcessor as InputMultiplexer
        im.removeProcessor(this)
        im.removeProcessor(uiStage)
        im.removeProcessor(mainStage)
    }

    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun dispose() {}

    // methods required by InputProcessor interface
    override fun keyDown(keycode: Int): Boolean { return false }
    override fun keyUp(keycode: Int): Boolean { return false }
    override fun keyTyped(character: Char): Boolean { return false }
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean { return false }
    override fun scrolled(amount: Int): Boolean { return false }
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean { return false }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }

    // Miscellaneous Utilities
    // Custom type checker
    fun isTouchDownEvent(e: Event): Boolean {
        return e is InputEvent && e.type == Type.touchDown
    }

    fun playConsecutiveAudio(music: Music) {
        if (!music.isPlaying) // only music have an isPlaying method
            music.play()
    }
}
