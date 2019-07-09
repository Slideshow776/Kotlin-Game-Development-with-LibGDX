package chapter8

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils

class LevelScreen : BaseScreen() {
    private lateinit var paddle: Paddle
    private lateinit var ball: Ball

    override fun initialize() {
        // background
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/space.png")
        BaseActor.setWorldBounds(background)

        // paddle
        paddle = Paddle(320f, 32f, mainStage)

        // walls
        Wall(0f, 0f, mainStage, 20f, 600f) // left wall
        Wall(780f, 0f, mainStage, 20f, 600f) // right wall
        Wall(0f, 550f, mainStage, 800f, 50f) // top wall

        // bricks
        val tempBrick = Brick(0f, 0f, mainStage)
        val brickWidth = tempBrick.width
        val brickHeight = tempBrick.height
        tempBrick.remove()

        val totalRows = 10
        val totalCols = 10
        val marginX = (800 - totalCols * brickWidth) / 2
        val marginY = (600 - totalRows * brickHeight) -120

        for (rowNum in 0 until totalRows) {
            for (colNum in 0 until totalCols) {
                val x = marginX + brickWidth * colNum
                val y = marginY + brickHeight * rowNum
                Brick(x, y, mainStage)
            }
        }

        // ball
        ball = Ball(0f, 0f, mainStage)
    }

    override fun update(dt: Float) {
        val mouseX = Gdx.input.x
        paddle.x = mouseX - paddle.width / 2
        paddle.boundToWorld()

        if ( ball.isPaused() ) {
            ball.x = paddle.x + paddle.width / 2 - ball.width / 2
            ball.y = paddle.y + paddle.height / 2 + ball.height / 2
        }

        for (wall: BaseActor in BaseActor.getList(mainStage, Wall::class.java.canonicalName)) {
            if (ball.overlaps(wall)) {
                ball.bounceOff((wall))
            }
        }

        for (brick: BaseActor in BaseActor.getList(mainStage, Brick::class.java.canonicalName)) {
            if (ball.overlaps(brick)) {
                ball.bounceOff(brick)
                brick.remove()
            }
        }

        if (ball.overlaps(paddle)) {
            val ballCenterX = ball.x + ball.width / 2
            val paddlePercentHit = (ballCenterX - paddle.x) / paddle.width
            val bounceAngle = MathUtils.lerp(150f, 30f, paddlePercentHit)
            ball.setMotionAngle(bounceAngle)
        }
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (ball.isPaused()) {
            ball.setPaused(false)
        }
        return false
    }
}
