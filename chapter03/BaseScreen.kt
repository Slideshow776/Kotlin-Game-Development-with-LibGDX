package chapter03

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Screen

abstract class BaseScreen : Screen {
    protected var mainStage: Stage
    protected var uiStage: Stage

    init {
        mainStage = Stage()
        uiStage = Stage()

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

    override fun hide() {}
    override fun show() {}
    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun dispose() {}
}
