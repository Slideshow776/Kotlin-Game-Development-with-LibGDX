package chapter15.spaceRocksParticles

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Warp(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var vertexShaderCode: String
    var fragmenterShaderCode: String
    var shaderProgram: ShaderProgram

    var time = .0f

    init {
        loadAnimationFromSheet("assets/warp.png", 4, 8, .05f, true)
        setScale(Constants.scale, Constants.scale)

        addAction(Actions.delay(1f))
        addAction(Actions.after(Actions.fadeOut(.5f)))
        addAction(Actions.after(Actions.removeActor()))

        vertexShaderCode = Gdx.files.internal("assets/shaders/default.vs").readString()
        fragmenterShaderCode = Gdx.files.internal("assets/shaders/wave.fs").readString()
        shaderProgram = ShaderProgram(vertexShaderCode, fragmenterShaderCode)
        if (!shaderProgram.isCompiled)
            println("Shader compile error: " + shaderProgram.log)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.shader = shaderProgram
        shaderProgram.setUniformf("u_time", time)
        shaderProgram.setUniformf("u_imageSize", Vector2(width, height))
        shaderProgram.setUniformf("u_amplitude", Vector2(1f, 1.5f))
        shaderProgram.setUniformf("u_wavelength", Vector2(40f, 40.5f))
        shaderProgram.setUniformf("u_velocity", Vector2(1f, 2f))
        super.draw(batch, parentAlpha)
        batch.shader = null
    }

    override fun act(dt: Float) {
        super.act(dt)
        time += dt
    }
}
