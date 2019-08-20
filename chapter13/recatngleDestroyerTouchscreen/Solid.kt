package chapter13.recatngleDestroyerTouchscreen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage

class Solid(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadTexture("assets/brick-gray.png")
        color = Color.FIREBRICK

        setSpeed(100f)
        setMotionAngle(0f)
        setBoundaryRectangle()
    }

    override fun act(dt: Float) {
        super.act(dt)
        changeDirection()
        applyPhysics(dt)
    }

    private fun changeDirection() {
        if (x < 20) {
            setMotionAngle(0f)
        } else if (x > 720) {
            setMotionAngle(180f)
        }
    }
}
