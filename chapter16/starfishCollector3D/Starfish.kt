package chapter16.starfishCollector3D

class Starfish(x: Float, y: Float, z: Float, s: Stage3D) : ObjModel(x, y, z, s) {
    init {
        loadObjModel("assets/star.obj")
        setScale(3f, 1f, 3f)
        setBasePolygon()
    }

    override fun act(dt: Float) {
        super.act(dt)
        turn(90 * dt)
    }
}
