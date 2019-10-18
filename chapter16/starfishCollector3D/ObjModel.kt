package chapter16.starfishCollector3D

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader

class ObjModel(x: Float, y: Float, z: Float, s: Stage3D) : BaseActor3D(x, y, z, s) {
    fun loadObjModel(fileName: String) {
        val loader = ObjLoader()
        val objModel = loader.loadModel(Gdx.files.internal(fileName), true)
        setModelInstance(ModelInstance(objModel))
    }
}
