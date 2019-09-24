package chapter15.starfishCollectorShaders

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram

class Turtle(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    var pause = false

    var vertexShaderCode: String
    var fragmenterShaderCode: String
    lateinit var shaderProgram: ShaderProgram

    init {
        val fileNames: Array<String> = Array()
        fileNames.add("assets/turtle-1.png")
        fileNames.add("assets/turtle-2.png")
        fileNames.add("assets/turtle-3.png")
        fileNames.add("assets/turtle-4.png")
        fileNames.add("assets/turtle-5.png")
        fileNames.add("assets/turtle-6.png")
        loadAnimationFromFiles(fileNames, .1f, true)

        setBoundaryPolygon(8)

        setAcceleration(400f) // time to reach max speed = 100/400 = .25 seconds
        setMaxSpeed(100f) // pixels/seconds
        setDeceleration(400f) // time to reach zero speed = 100/400 = .25 seconds

        vertexShaderCode = Gdx.files.internal("assets/shaders/default.vs").readString()

        // fragmenterShaderCode = Gdx.files.internal("assets/shaders/default.fs").readString()
        // fragmenterShaderCode = Gdx.files.internal("assets/shaders/grayscale.fs").readString()
        fragmenterShaderCode = Gdx.files.internal("assets/shaders/invert.fs").readString()

        shaderProgram = ShaderProgram(vertexShaderCode, fragmenterShaderCode)
        if (!shaderProgram.isCompiled)
            println("Shader compile error: " + shaderProgram.log)
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (!pause) {
            if (Gdx.input.isKeyPressed(Keys.W))
                accelerateAtAngle(90f)
            if (Gdx.input.isKeyPressed(Keys.A))
                accelerateAtAngle(180f)
            if (Gdx.input.isKeyPressed(Keys.S))
                accelerateAtAngle(270f)
            if (Gdx.input.isKeyPressed(Keys.D))
                accelerateAtAngle(0f)
        }
        applyPhysics(dt)

        setAnimationPaused(!isMoving())

        if (getSpeed() > 0)
            rotation = getMotionAngle()

        boundToWorld()
        alignCamera()
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.shader = shaderProgram
        super.draw(batch, parentAlpha)
        batch.shader = null
    }
}