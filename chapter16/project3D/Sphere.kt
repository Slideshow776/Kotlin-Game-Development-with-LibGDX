package chapter16.project3D

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3

class Sphere(x: Float, y: Float, z: Float, s: Stage3D) : BaseActor3D(x, y, z, s) {
    init {
        val modelBuilder = ModelBuilder()
        val sphereMaterial = Material()

        val usageCode = Usage.Position + Usage.ColorPacked + Usage.Normal + Usage.TextureCoordinates

        val radius = 1f
        val model = modelBuilder.createSphere(
            radius,
            radius,
            radius,
            32,
            32,
            sphereMaterial,
            usageCode.toLong()
        )
        val position = Vector3(0f, 0f, 0f)
        setModelInstance(ModelInstance(model, position))
    }
}
