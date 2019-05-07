package chapter4

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Ufo(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/ufo.png")
        setScale(Constants.scale, Constants.scale)

        setBoundaryPolygon(8)

        setSpeed(80f)
        setDeceleration(0f)
        calculateMotionAngle()

        addAction(
            Actions.forever(Actions.rotateBy(.25f))
        )
        var pulse = Actions.sequence(
            Actions.color(Color.WHITE, 1.5f),
            Actions.color(Color(1f, .388f, .278f, 1f), 1.5f) // Tomato red
        )
        addAction(Actions.forever(pulse))

        addAction(Actions.delay(20f))
        addAction(Actions.after(Actions.removeActor()))
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }

    /*
    * Makes the Ufo fly towards the center of the screen
    * The point (x2, y2) is the center of the screen.
    * https://math.stackexchange.com/questions/707673/find-angle-in-degrees-from-one-point-to-another-in-2d-space
    * */
    private fun calculateMotionAngle() {
        val y1 = y
        val y2 = 300f
        val x1 = x
        val x2 = 400f

        val m = (y2 - y1) / (x2 - x1)
        val theta = Math.atan(m.toDouble())

        if (x2 - x1 > 0)
            setMotionAngle(Math.toDegrees(theta).toFloat())
        else
            setMotionAngle(Math.toDegrees(theta).toFloat()-180)
    }
}
