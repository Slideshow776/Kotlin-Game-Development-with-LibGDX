package chapter16.project3D

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.math.Vector3
import java.util.ArrayList;

class Stage3D {
    private var environment: Environment
    private var camera: PerspectiveCamera
    private var modelBatch: ModelBatch
    private var actorList: ArrayList<BaseActor3D>

    init {
        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, .7f, .7f, .7f, 1f))

        val dlight = DirectionalLight()
        val lightColor = Color(.9f, .9f, .9f, 1f)
        val lightVector = Vector3(-1f, -.75f, -.25f)
        dlight.set(lightColor, lightVector)
        environment.add(dlight)

        camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(10f, 10f, 10f)
        camera.lookAt(0f, 0f, 0f)
        camera.near = .01f
        camera.far = 1000f
        camera.update()

        modelBatch = ModelBatch()

        actorList = ArrayList()
    }

    fun act(dt: Float) {
        camera.update()
        for (ba in this.actorList)
            ba.act(dt)
    }

    fun draw() {
        modelBatch.begin(camera)
        for (ba in this.actorList)
            ba.draw(modelBatch, environment)
        modelBatch.end()
    }

    fun addActor(ba: BaseActor3D) { actorList.add(ba) }
    fun removeActor(ba: BaseActor3D) { actorList.remove(ba) }
    fun getActors(): ArrayList<BaseActor3D> { return actorList }

    fun setCameraPosition(x: Float, y: Float, z: Float) { camera.position.set(x, y, z) }
    fun setCameraPosition(v: Vector3) { camera.position.set(v) }
    fun moveCamera(x: Float, y: Float, z: Float) { camera.position.add(x, y, z) }
    fun moveCamera(v: Vector3) { camera.position.add(v) }

    fun moveCameraForward(dist: Float) {
        val forward = Vector3(camera.direction.x, 0f, camera.direction.z).nor() // .nor() => normalizes the values
        moveCamera(forward.scl(dist)) // .scl(Float) => scales vector by value
    }

    fun moveCameraRight(dist: Float) {
        val right = Vector3(camera.direction.z, 0f, -camera.direction.x).nor()
        moveCamera(right.scl(dist))
    }

    fun moveCameraUp(dist: Float) { moveCamera(0f, dist, 0f) }
    fun turnCamera(angle: Float) { camera.rotate(Vector3.Y, -angle) }

    fun tiltCamera(angle: Float) {
        val right = Vector3(camera.direction.z, 0f, -camera.direction.x)
        camera.direction.rotate(right, angle)
    }

    fun setCameraDirection(v: Vector3) {
        camera.lookAt(v)
        camera.up.set(0f, 1f, 0f)
    }

    fun setCameraDirection(x: Float, y: Float, z: Float) { setCameraDirection(Vector3(x, y, z)) }
}
