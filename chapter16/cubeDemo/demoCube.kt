package chapter16.project3D

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3

class demoCube : ApplicationListener {
    lateinit var environment: Environment
    lateinit var camera: PerspectiveCamera
    lateinit var modelBatch: ModelBatch
    lateinit var boxInstance: ModelInstance

    override fun create() {
        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, .4f, .4f, .4f, 1f))

        val dLight = DirectionalLight()
        val lightColor = Color(.75f, .75f, .75f, 1f)
        val lightVector = Vector3(-1f, -.75f, -.25f)
        dLight.set(lightColor, lightVector)
        environment.add(dLight)

        camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.near = .1f
        camera.far = 1000f
        camera.position.set(10f, 10f, 10f)
        camera.lookAt(0f, 0f, 0f)
        camera.update()

        modelBatch = ModelBatch()

        val modelBuilder = ModelBuilder()

        val boxMaterial = Material()
        boxMaterial.set(ColorAttribute.createDiffuse(Color.PINK))

        val usageCode = Usage.Position + Usage.ColorPacked + Usage.Normal

        val boxModel = modelBuilder.createBox(5f, 5f, 5f, boxMaterial, usageCode.toLong())
        boxInstance = ModelInstance(boxModel)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        modelBatch.begin(camera)
        modelBatch.render(boxInstance, environment)
        modelBatch.end()
    }

    override fun pause() {}
    override fun resume() {}
    override fun resize(width: Int, height: Int) {}
    override fun dispose() {}
}
