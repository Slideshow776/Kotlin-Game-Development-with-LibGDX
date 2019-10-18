package chapter16.starfishCollector3D

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.math.*
import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector

open class BaseActor3D(x: Float, y: Float, z: Float, s: Stage3D) {
    private var modelData: ModelInstance?
    private var position: Vector3
    private var rotation: Quaternion
    private var scale: Vector3
    protected var stage: Stage3D

    private lateinit var boundingPolygon: Polygon

    init {
        modelData = null
        position = Vector3(x, y, z)
        rotation = Quaternion()
        scale = Vector3(1f, 1f, 1f)
        stage = s
        s.addActor(this)
    }

    fun setModelInstance(m: ModelInstance) {
        modelData = m
    }

    fun calculateTransform(): Matrix4 {
        return Matrix4(position, rotation, scale)
    }

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

    fun act(dt: Float) {
        modelData?.transform?.set(calculateTransform())
    }

    fun draw(batch: ModelBatch, env: Environment) {
        batch.render(modelData, env)
    }

    fun getPosition(): Vector3 {
        return position
    }

    fun setPosition(v: Vector3) {
        position.set(v)
    }

    fun moveBy(v: Vector3) {
        position.add(v)
    }

    fun moveBy(x: Float, y: Float, z: Float) {
        moveBy(Vector3(x, y, z))
    }

    fun getTurnAngle(): Float {
        return rotation.getAngleAround(0f, -1f, 0f)
    }

    fun setTurnAngle(degrees: Float) {
        rotation.set(Quaternion(Vector3.Y, degrees))
    }

    fun turn(degrees: Float) {
        rotation.mul(Quaternion(Vector3.Y, -degrees))
    }

    fun moveForward(dist: Float) {
        moveBy(rotation.transform(Vector3(0f, 0f, -1f)).scl(dist))
    }

    fun moveUp(dist: Float) {
        moveBy(rotation.transform(Vector3(0f, 1f, 0f)).scl(dist))
    }

    fun moveRight(dist: Float) {
        moveBy(rotation.transform(Vector3(1f, 0f, 0f)).scl(dist))
    }

    fun setScale(x: Float, y: Float, z: Float) {
        scale.set(x, y, z)
    }

    fun setBaseRectangle() {
        val modelBounds = modelData?.calculateBoundingBox(BoundingBox())
        val max = modelBounds?.max
        val min = modelBounds?.min
        val vertices = floatArrayOf(max!!.x, max.z, min!!.x, max.z, min.x, min.z, max.x, min.z)
        boundingPolygon = Polygon(vertices)
        boundingPolygon.setOrigin(0f, 0f)
    }

    fun setBasePolygon() {
        val modelBounds = modelData?.calculateBoundingBox(BoundingBox())
        val max = modelBounds?.max
        val min = modelBounds?.min

        val a = .75f
        val vertices = floatArrayOf(
            max!!.x, 0f, a * max.x, a * max.z, 0f, max.z,
            a * min!!.x, a * max.z, min.x, 0f, a * min.x,
            a * min.z, 0f, min.z, a * max.x,  a * min.z
        )
        boundingPolygon = Polygon(vertices)
        boundingPolygon.setOrigin(0f, 0f)
    }

    fun getBoundaryPolygon(): Polygon {
        boundingPolygon.setPosition(position.x, position.z)
        boundingPolygon.rotation = getTurnAngle()
        boundingPolygon.setScale(scale.x, scale.z)
        return boundingPolygon
    }

    fun overlaps(other: BaseActor3D): Boolean {
        val poly1 = this.getBoundaryPolygon()
        val poly2 = other.getBoundaryPolygon()

        if (!poly1.boundingRectangle.overlaps(poly2.boundingRectangle))
            return false

        val mtv = MinimumTranslationVector()

        return Intersector.overlapConvexPolygons(poly1, poly2, mtv)
    }

    fun preventOverlap(other: BaseActor3D) {
        val poly1 = this.getBoundaryPolygon()
        val poly2 = other.getBoundaryPolygon()

        // initial test to improve performance
        if (!poly1.boundingRectangle.overlaps(poly2.boundingRectangle))
            return

        val mtv = MinimumTranslationVector()
        val polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv)

        if (polygonOverlap)
            this.moveBy(mtv.normal.x * mtv.depth, 0f, mtv.normal.y * mtv.depth)
    }
}
