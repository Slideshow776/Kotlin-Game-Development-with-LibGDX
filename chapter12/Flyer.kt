package chapter12

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage

class Flyer(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadAnimationFromSheet("assets/enemy-flyer.png", 1, 4, .05f, true)
        setSize(48f, 48f)
        setBoundaryPolygon(6)

        setSpeed(MathUtils.random(50f, 80f))
        setMotionAngle(MathUtils.random(0f, 360f))
    }

    override fun act(dt: Float) {
        super.act(dt)
        if (MathUtils.random(1, 120) == 1) {
            setMotionAngle(MathUtils.random(0f, 360f))
        }
        applyPhysics(dt)
        boundToWorld()
    }
}
