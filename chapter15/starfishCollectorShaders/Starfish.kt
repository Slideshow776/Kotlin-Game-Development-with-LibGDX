package chapter15.starfishCollectorShaders

import chapter15.starfishCollectorShaders.BaseActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Starfish(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private var collected: Boolean = false

    var vertexShaderCode: String
    var fragmenterShaderCode: String
    var shaderProgram: ShaderProgram

    var time = .0f

    init {
        loadTexture("assets/starfish.png")
        setBoundaryPolygon(8)

        // Actions
        val spin: Action = Actions.rotateBy(30.toFloat(), 1.toFloat())
        this.addAction(Actions.forever(spin))

        vertexShaderCode = Gdx.files.internal("assets/shaders/default.vs").readString()
        fragmenterShaderCode = Gdx.files.internal("assets/shaders/glow-pulse.fs").readString()
        shaderProgram = ShaderProgram(vertexShaderCode, fragmenterShaderCode)
        if (!shaderProgram.isCompiled)
            println("Shader compile error: " + shaderProgram.log)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.shader = shaderProgram
        shaderProgram.setUniformf("u_time", time)
        shaderProgram.setUniformf("u_imageSize", Vector2(width, height))
        shaderProgram.setUniformf("u_glowRadius", 1f)
        super.draw(batch, parentAlpha)
        batch.shader = null
    }

    override fun act(dt: Float) {
        super.act(dt)
        time += dt
    }

    fun isCollected() = collected

    fun collect() {
        collected = true
        clearActions()
        addAction(Actions.fadeOut(1f))
        addAction(Actions.after(Actions.removeActor()))
    }
}
