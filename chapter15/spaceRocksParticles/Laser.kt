package chapter15.spaceRocksParticles

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Laser(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {


    var vertexShaderCode: String
    var fragmenterShaderCode: String
    var shaderProgram: ShaderProgram

    var time = .0f

    init {
        loadTexture("assets/laser.png")
        setScale(Constants.scale * .5f, Constants.scale * .5f)

        addAction(Actions.delay(1f))
        addAction(Actions.after(Actions.fadeOut(.25f)))
        addAction(Actions.after(Actions.removeActor()))

        setSpeed(800f)
        setMaxSpeed(800f)
        setDeceleration(0f)

        vertexShaderCode = Gdx.files.internal("assets/shaders/default.vs").readString()
        fragmenterShaderCode = Gdx.files.internal("assets/shaders/glow-pulse.fs").readString()
        shaderProgram = ShaderProgram(vertexShaderCode, fragmenterShaderCode)
        if (!shaderProgram.isCompiled)
            println("Shader compile error: " + shaderProgram.log)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
        wrapAroundWorld()

        time += dt
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.shader = shaderProgram
        shaderProgram.setUniformf("u_time", time)
        shaderProgram.setUniformf("u_imageSize", Vector2(width, height))
        shaderProgram.setUniformf("u_glowRadius", 5f)
        super.draw(batch, parentAlpha)
        batch.shader = null
    }
}
