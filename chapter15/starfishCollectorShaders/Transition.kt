package chapter15.starfishCollectorShaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Transition(x: Float, y: Float, assetPath: String, s: Stage) : BaseActor(x, y, s) {
    var vertexShaderCode: String
    var fragmenterShaderCode: String
    var shaderProgram: ShaderProgram

    var time = 0f
    var enabled = false // triggers the effect
    var wayIn = false // if transitioning in or out
    var duration = .4f // [seconds]

    init {
        loadTexture(assetPath)
        setSize(800f, 600f)

        vertexShaderCode = Gdx.files.internal("assets/shaders/transition.vs").readString()
        fragmenterShaderCode = Gdx.files.internal("assets/shaders/transition.fs").readString()
        shaderProgram = ShaderProgram(vertexShaderCode, fragmenterShaderCode)
        if (!shaderProgram.isCompiled)
            println("Shader compile error: " + shaderProgram.log)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (enabled) {
            val percent = if (wayIn) {
                Math.min(1f, time / duration)
            } else {
                Math.max(0f, 1 - time / duration)
            }
            batch.shader = shaderProgram
            shaderProgram.setUniformf("alpha", percent)
            super.draw(batch, parentAlpha)
            batch.shader = null

            if (wayIn) {
                if (percent >= .98f)
                    addAction(Actions.delay(.1f, Actions.run { this.remove() }))
            } else {
                if (percent <= .02f)
                    addAction(Actions.delay(.1f, Actions.run { this.remove() }))
            }
        }
    }

    override fun act(dt: Float) {
        super.act(dt)
        time += dt
    }
}
