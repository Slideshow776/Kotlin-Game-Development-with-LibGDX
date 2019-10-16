package chapter16.starfishCollector3D

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color

class DemoScreen : BaseScreen() {
    lateinit var player: BaseActor3D

    override fun initialize() {
        val screen = Box(0f, 0f, 0f, mainStage3D)
        screen.setScale(16f, 12f, 0.1f)
        screen.loadTexture("assets/starfish-collector.png")

        val markerO = Box(0f, 0f, 0f, mainStage3D)
        markerO.setColor(Color.BROWN)
        markerO.loadTexture("assets/crate.jpg")

        val markerX = Box(5f, 0f, 0f, mainStage3D)
        markerX.setColor(Color.RED)
        markerX.loadTexture("assets/crate.jpg")

        val markerY = Box(0f, 5f, 0f, mainStage3D)
        markerY.setColor(Color.GREEN)
        markerY.loadTexture("assets/crate.jpg")

        val markerZ = Box(0f, 0f, 5f, mainStage3D)
        markerZ.setColor(Color.BLUE)
        markerZ.loadTexture("assets/crate.jpg")

        player = Sphere(0f, 1f, 8f, mainStage3D)
        player.loadTexture("assets/sphere-pos-neg.png")

        mainStage3D.setCameraPosition(3f, 4f, 10f)
        mainStage3D.setCameraDirection(0f, 0f, 0f)
    }

    override fun update(dt: Float) {
        val speed = 3f
        val rotateSpeed = 45f

        if ( !(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) )
        {
            if ( Gdx.input.isKeyPressed(Keys.W) )
                player.moveForward( speed * dt );
            if ( Gdx.input.isKeyPressed(Keys.S) )
                player.moveForward( -speed * dt );
            if ( Gdx.input.isKeyPressed(Keys.A) )
                player.moveRight( -speed * dt );
            if ( Gdx.input.isKeyPressed(Keys.D) )
                player.moveRight( speed * dt );

            if ( Gdx.input.isKeyPressed(Keys.Q) )
                player.turn( -rotateSpeed * dt );
            if ( Gdx.input.isKeyPressed(Keys.E) )
                player.turn( rotateSpeed * dt );

            if ( Gdx.input.isKeyPressed(Keys.R) )
                player.moveUp( speed * dt );
            if ( Gdx.input.isKeyPressed(Keys.F) )
                player.moveUp( -speed * dt );
        }

        if ( Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT) )
        {
            if (Gdx.input.isKeyPressed(Keys.W))
                mainStage3D.moveCameraForward( speed * dt );
            if (Gdx.input.isKeyPressed(Keys.S))
                mainStage3D.moveCameraForward( -speed * dt );
            if (Gdx.input.isKeyPressed(Keys.A))
                mainStage3D.moveCameraRight( speed * dt );
            if (Gdx.input.isKeyPressed(Keys.D))
                mainStage3D.moveCameraRight( -speed * dt );

            if (Gdx.input.isKeyPressed(Keys.R))
                mainStage3D.moveCameraUp( speed * dt );
            if (Gdx.input.isKeyPressed(Keys.F))
                mainStage3D.moveCameraUp( -speed * dt );

            if (Gdx.input.isKeyPressed(Keys.Q))
                mainStage3D.turnCamera(-rotateSpeed * dt);
            if (Gdx.input.isKeyPressed(Keys.E))
                mainStage3D.turnCamera(rotateSpeed * dt);

            if (Gdx.input.isKeyPressed(Keys.T))
                mainStage3D.tiltCamera(-rotateSpeed * dt);
            if (Gdx.input.isKeyPressed(Keys.G))
                mainStage3D.tiltCamera(rotateSpeed * dt);
        }
    }
}
