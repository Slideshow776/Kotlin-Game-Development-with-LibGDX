package chapter4

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

class Spaceship(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {

    init {
        // super(x, y, s)
        loadTexture("assets/spaceship.png")
        setBoundaryPolygon(8)

        setAcceleration(220f)
        setMaxSpeed(200f)
        setDeceleration(1f)
    }

    override fun act(dt: Float) {
        super.act(dt)

        var degreesPerSecond = 140f // rotation speed
        if(Gdx.input.isKeyPressed((Keys.A)))
            rotateBy(degreesPerSecond * dt)
        if(Gdx.input.isKeyPressed((Keys.D)))
            rotateBy(-degreesPerSecond * dt)
        if(Gdx.input.isKeyPressed((Keys.W)))
            accelerateAtAngle(rotation)

        applyPhysics(dt)
        wrapArpundWorld()
    }
}
