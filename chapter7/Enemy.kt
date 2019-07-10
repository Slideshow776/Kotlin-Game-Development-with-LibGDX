package chapter7

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array

class Enemy(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        val fileNames = Array<String>()
        fileNames.add("assets/planeRed0.png")
        fileNames.add("assets/planeRed1.png")
        fileNames.add("assets/planeRed2.png")
        fileNames.add("assets/planeRed1.png")
        loadAnimationFromFiles(fileNames, .1f, true)

        setSpeed(100f)
        setMotionAngle(180f)
        setBoundaryPolygon(8)
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }
}
