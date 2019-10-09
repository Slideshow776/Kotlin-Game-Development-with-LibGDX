package chapter16.project3D

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3

class BaseActor3D(x: Float, y: Float, z: Float) {
    private var modelData: ModelInstance?
    private var position: Vector3
    private var rotation: Quaternion
    private var scale: Vector3

    init {
        modelData = null
        position = Vector3(x, y, z)
        rotation = Quaternion()
        scale = Vector3(1f, 1f, 1f)
    }

    fun setModelInstance(m: ModelInstance) { modelData = m }
    fun calculateTransform():Matrix4 { return Matrix4(position, rotation, scale)}

    fun setColor(c: Color) {
        for (m in modelData?.materials!!)
            m.set(ColorAttribute.createDiffuse(c))
    }

    fun loadTexture(filename: String) {
        val tex = Texture(Gdx.files.internal(filename), true)
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        for (m in modelData?.materials!!)
            m.set(TextureAttribute.createDiffuse(tex))
    }

    fun act(dt: Float) { modelData?.transform?.set(calculateTransform()) }
    fun draw(batch: ModelBatch, env: Environment) { batch.render(modelData, env) }

    fun getPosition():Vector3 { return position }
    fun setPosition(v: Vector3) { position.set(v) }
    fun moveBy(v: Vector3) { position.add(v) }
    fun moveBy(x: Float, y: Float, z: Float) { moveBy(Vector3(x, y, z)) }

    fun getTurnAngle(): Float { return rotation.getAngleAround(0f, -1f, 0f) }
    fun setTurnAngle(degrees: Float) { rotation.set(Quaternion(Vector3.Y, degrees)) }
    fun turn(degrees: Float) { rotation.mul(Quaternion(Vector3.Y, -degrees)) }

    fun moveForward(dist: Float) { moveBy(rotation.transform(Vector3(0f, 0f, -1f)).scl(dist)) }
    fun moveUp(dist: Float) { moveBy(rotation.transform(Vector3(0f, 1f, 0f)).scl(dist)) }
    fun moveRight(dist: Float) { moveBy(rotation.transform(Vector3(1f, 0f, 0f)).scl(dist)) }
    fun setScale(x: Float, y: Float, z: Float) { scale.set(x, y, z) }
}
