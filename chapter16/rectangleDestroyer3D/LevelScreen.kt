package chapter16.rectangleDestroyer3D

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3

class LevelScreen : BaseScreen() {

    private lateinit var paddle: Box
    private lateinit var ball: Ball
    private lateinit var wallTop: BaseActor3D
    private lateinit var wallLeft: BaseActor3D
    private lateinit var wallRight: BaseActor3D
    private lateinit var wallBottom: BaseActor3D

    private val ballWidth = .6f
    private val paddleWidth = 2f

    private val controlSpeed = 6f

    override fun initialize() {
        // background
        val screen = Box(0f, 0f, 0f, mainStage3D)
        screen.setScale(16f, 12f, 0.1f)
        screen.loadTexture("assets/space.png")

        // bricks
        val tma = TilemapActor("assets/map.tmx")
        for (obj in tma.getTileList("brick")) {
            val props = obj.properties
            val b = Brick(
                ((props.get("x") as Float) / 50) - 7.6f,
                ((props.get("y") as Float) / 50) - 4.5f,
                0f,
                mainStage3D
            )

            when (props.get("color")) {
                "red" -> b.setColor(Color.RED)
                "orange" -> b.setColor(Color.ORANGE)
                "yellow" -> b.setColor(Color.YELLOW)
                "green" -> b.setColor(Color.GREEN)
                "blue" -> b.setColor(Color.BLUE)
                "purple" -> b.setColor(Color.PURPLE)
                "white" -> b.setColor(Color.WHITE)
                "gray" -> b.setColor(Color.GRAY)
                "pink" -> b.setColor(Color.PINK)
                else -> println("LevelScreen: Error => Could not set color ${props.get("color")} for brick!")
            }
        }

        // ball
        ball = Ball(0f, -5.8f, 0f, mainStage3D)

        // paddle
        paddle = Box(0f, -5.25f, 0f, mainStage3D)
        paddle.loadTexture("assets/brick-gray.png")
        paddle.setColor(Color.GOLDENROD)
        paddle.setScale(2f, .5f, .5f)
        paddle.setBaseRectangle()

        // walls
        wallTop = BaseActor3D(0f, 6.3f, 0f, mainStage3D)
        val modelBuilder = ModelBuilder()
        val boxMaterial = Material()

        val usageCode =
            VertexAttributes.Usage.Position + VertexAttributes.Usage.ColorPacked + VertexAttributes.Usage.Normal + VertexAttributes.Usage.TextureCoordinates
        var boxModel = modelBuilder.createBox(16f, .5f, 1f, boxMaterial, usageCode.toLong())
        val position = Vector3(0f, 0f, 0f)
        wallTop.setModelInstance(ModelInstance(boxModel, position))
        // wallTop.setColor(Color.BLACK)
        wallTop.setBaseRectangle()

        wallBottom = BaseActor3D(0f, -6.3f, 0f, mainStage3D)
        wallBottom.setModelInstance(ModelInstance(boxModel, position))
        wallBottom.setBaseRectangle()

        boxModel = modelBuilder.createBox(.5f, 16f, 1f, boxMaterial, usageCode.toLong())

        wallLeft = BaseActor3D(-8.1f, 0f, 0f, mainStage3D)
        wallLeft.setModelInstance(ModelInstance(boxModel, position))
        wallLeft.setBaseRectangle()

        wallRight = BaseActor3D(8.1f, 0f, 0f, mainStage3D)
        wallRight.setModelInstance(ModelInstance(boxModel, position))
        wallRight.setBaseRectangle()

        // camera
        mainStage3D.setCameraPosition(0f, 0f, 9.1f)
        mainStage3D.setCameraDirection(0f, 0f, 0f)
    }

    override fun update(dt: Float) {
        controls(dt)

        // game state
        if (ball.isPaused()) {
            ball.getPosition().x = paddle.getPosition().x + .5f / 2 - .6f / 2
            ball.getPosition().y = (paddle.getPosition().y + .5f / 2 + .6f / 2)
        }

        if (ball.overlaps(paddle)) {
            val ballCenterX = ball.getPosition().x + 2.1f / 2 // 2.1f just works... (should be ball.width)
            val paddlePercentHit = (ballCenterX - paddle.getPosition().x) / paddleWidth
            val bounceAngle = MathUtils.lerp(150f, 30f, paddlePercentHit)
            ball.setMotionAngle(bounceAngle)
        }

        if (ball.overlaps(wallTop)) ball.bounceOff(wallTop)
        if (ball.overlaps(wallBottom)) ball.setPaused(true)
        if (ball.overlaps(wallLeft)) ball.bounceOff(wallLeft)
        if (ball.overlaps(wallRight)) ball.bounceOff(wallRight)
        paddle.preventOverlap(wallRight)
        paddle.preventOverlap(wallLeft)

        for (brick in BaseActor3D.getList(mainStage3D, Brick::class.java.canonicalName)) {
            if (ball.overlaps(brick)) {
                ball.bounceOff(brick)
                brick.remove()
            }
        }
    }

    private fun controls(dt: Float) {
        if (Gdx.input.isKeyPressed(Keys.A))
            paddle.moveRight(-controlSpeed * dt);
        if (Gdx.input.isKeyPressed(Keys.D))
            paddle.moveRight(controlSpeed * dt);
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            if (ball.isPaused()) {
                ball.setPaused(false)
            }
        }
    }
}
