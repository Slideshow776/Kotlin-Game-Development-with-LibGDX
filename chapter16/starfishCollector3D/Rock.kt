package chapter16.starfishCollector3D

class Rock(x: Float, y: Float, z: Float, s: Stage3D) : ObjModel(x, y, z, s) {
    init {
        loadObjModel("assets/rock.obj")
        setBasePolygon()
        setScale(3f, 3f, 3f)
    }
}
