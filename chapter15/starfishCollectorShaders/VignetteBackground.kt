package chapter15.starfishCollectorShaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.Stage

class VignetteBackground(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var vertexShaderCode: String
    var fragmenterShaderCode: String
    var shaderProgram: ShaderProgram

    init {
        loadTexture("assets/oceanside.png")
        setSize(800f, 600f)
        setOpacity(0f)
        setWorldBounds(this)

        ShaderProgram.pedantic = false
        vertexShaderCode = Gdx.files.internal("assets/shaders/vignette.vs").readString()
        fragmenterShaderCode = Gdx.files.internal("assets/shaders/vignette.fs").readString()
        shaderProgram = ShaderProgram(vertexShaderCode, fragmenterShaderCode)
        if (!shaderProgram.isCompiled)
            println("Shader compile error: " + shaderProgram.log)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.shader = shaderProgram
        shaderProgram.setUniformf(
            "resolution",
            800f,
            600f
        );
        super.draw(batch, parentAlpha)
        batch.shader = null
    }
}
