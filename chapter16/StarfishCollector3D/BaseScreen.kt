package chapter16.project3D

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table

abstract class BaseScreen : Screen, InputProcessor {
    protected lateinit var mainStage3D: Stage3D
    protected lateinit var uiStage: Stage
    protected lateinit var uiTable: Table

    init {
        mainStage3D = Stage3D()
        uiStage = Stage()

        uiTable = Table()
        uiTable.setFillParent(true)
        uiStage.addActor(uiTable)

        initialize()
    }

    abstract fun initialize()
    abstract fun update (dt: Float)

    // gameloop method
    override fun render(dt: Float) {
        // limit amount of time that can pass while window is being dragged
        var delta = Math.min(dt, 1/30f)

        // act methods
        uiStage.act(delta)
        mainStage3D.act(delta)

        // defined by game-specific classes
        update(delta)

        // render
        Gdx.gl.glClearColor(.5f, .5f, .5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT + GL20.GL_DEPTH_BUFFER_BIT)

        // draw the graphics
        mainStage3D.draw()
        uiStage.draw()
    }

    override fun resize(width: Int, height: Int) { uiStage.viewport.update(width, height, true) }
    override fun pause() {}
    override fun resume() {}
    override fun dispose() {}

    override fun show() {
        val im = Gdx.input.inputProcessor as InputMultiplexer
        im.addProcessor(this)
        im.addProcessor(uiStage)
    }

    override fun hide() {
        val im = Gdx.input.inputProcessor as InputMultiplexer
        im.removeProcessor(this)
        im.removeProcessor(uiStage)
    }

    override fun keyDown(keycode: Int): Boolean { return false}
    override fun keyUp(keycode: Int): Boolean { return false }
    override fun keyTyped(character: Char): Boolean { return false }
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean { return false }
    override fun scrolled(amount: Int): Boolean { return false }
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean { return false }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }
}
