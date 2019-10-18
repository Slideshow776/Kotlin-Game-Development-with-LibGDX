package chapter16.starfishCollector3D

class Turtle(x: Float, y: Float, z: Float, s: Stage3D) : ObjModel(x, y, z, s){
    init {
        loadObjModel("assets/turtle.obj")
        setBasePolygon()
    }
}
