package chapter13.spaceRocksTouchscreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport

abstract class BaseTouchScreen : BaseScreen() {
    protected lateinit var controlStage: Stage
    protected lateinit var controlTable: Table

    // run this method during initialize
    fun initializeControlArea() {
        controlStage = Stage(FitViewport(800f, 800f))
        controlTable = Table()
        controlTable.setFillParent(true)
        controlStage.addActor(controlTable)
    }

    override fun show() {
        super.show()
        val im  = Gdx.input.inputProcessor as InputMultiplexer
        im.addProcessor(controlStage)
    }

    override fun hide() {
        super.hide()
        val im  = Gdx.input.inputProcessor as InputMultiplexer
        im.removeProcessor(controlStage)
    }

    override fun render(dt: Float) {
        super.render(dt)

        // act methods
        uiStage.act(dt)
        mainStage.act(dt)
        controlStage.act(dt)

        // defined by user
        update(dt)

        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // set the drawing regions and draw the graphics
        Gdx.gl.glViewport(0, 200, 800, 600)
        mainStage.draw()
        uiStage.draw()

        Gdx.gl.glViewport(0, 0, 800, 800)
        controlStage.draw()
    }
}
