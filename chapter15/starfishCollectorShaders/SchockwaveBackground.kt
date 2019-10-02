package chapter15.starfishCollectorShaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction

class SchockwaveBackground(x: Float, y: Float, texturePath: String, s: Stage) : BaseActor(x, y, s) {
    var vertexShaderCode: String
    var fragmenterShaderCode: String
    var shaderProgram: ShaderProgram

    var time = .0f
    var shockWavePositionX = .0f
    var shockWavePositionY = .0f
    var disabled = true

    init {
        if (!texturePath.isBlank())
            loadTexture(texturePath)

        ShaderProgram.pedantic = false
        vertexShaderCode = Gdx.files.internal("assets/shaders/default.vs").readString()
        fragmenterShaderCode = Gdx.files.internal("assets/shaders/shockwave.fs").readString()
        shaderProgram = ShaderProgram(vertexShaderCode, fragmenterShaderCode)
        if (!shaderProgram.isCompiled)
            println("Shader compile error: " + shaderProgram.log)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (disabled)
            super.draw(batch, parentAlpha)
        else {
            batch.shader = shaderProgram
            shaderProgram.setUniformf("time", time)
            shaderProgram.setUniformf("center", Vector2(shockWavePositionX, shockWavePositionY))
            shaderProgram.setUniformf("shockParams", Vector3(10f, .8f, .1f))
            super.draw(batch, parentAlpha)
            batch.shader = null
        }
    }

    override fun act(dt: Float) {
        super.act(dt)
        time += dt
    }

    fun start(posX: Float, posY: Float) {
        this.shockWavePositionX = posX
        this.shockWavePositionY = posY
        val enable = RunnableAction()
        enable.runnable = Runnable { disabled = true }
        this.addAction(Actions.delay(1f, enable))
        disabled = false
        time = 0f

    }
}
