package chapter13.starfishCollectorGamepad

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.math.Vector2

class Turtle(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    var pause = false

    init {
        val fileNames: Array<String> = Array()
        fileNames.add("assets/turtle-1.png")
        fileNames.add("assets/turtle-2.png")
        fileNames.add("assets/turtle-3.png")
        fileNames.add("assets/turtle-4.png")
        fileNames.add("assets/turtle-5.png")
        fileNames.add("assets/turtle-6.png")
        loadAnimationFromFiles(fileNames, .1f, true)

        setBoundaryPolygon(8)

        setAcceleration(400f) // time to reach max speed = 100/400 = .25 seconds
        setMaxSpeed(100f) // pixels/seconds
        setDeceleration(400f) // time to reach zero speed = 100/400 = .25 seconds
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (!pause) {
            if (Controllers.getControllers().size > 0) {
                val gamepad = Controllers.getControllers()[0]
                val xAxis = gamepad.getAxis(XBoxGamepad.AXIS_LEFT_X)
                val yAxis =
                    -gamepad.getAxis(XBoxGamepad.AXIS_LEFT_Y) // the orientation of most controllers is the opposite of LibGDX libraries
                val direction = Vector2(xAxis, yAxis)

                val length = direction.len()
                val deadZone = .1f
                if (length > deadZone) {
                    setSpeed(length * 100)
                    setMotionAngle(direction.angle())
                }
            } else { // fallback to keyboard
                if (Gdx.input.isKeyPressed(Keys.W))
                    accelerateAtAngle(90f)
                if (Gdx.input.isKeyPressed(Keys.A))
                    accelerateAtAngle(180f)
                if (Gdx.input.isKeyPressed(Keys.S))
                    accelerateAtAngle(270f)
                if (Gdx.input.isKeyPressed(Keys.D))
                    accelerateAtAngle(0f)
            }
        }
        applyPhysics(dt)

        setAnimationPaused(!isMoving())

        if (getSpeed() > 0)
            rotation = getMotionAngle()

        boundToWorld()
        alignCamera()
    }
}