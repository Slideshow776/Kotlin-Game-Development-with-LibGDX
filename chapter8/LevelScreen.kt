package chapter8

import com.badlogic.gdx.Gdx

class LevelScreen : BaseScreen() {
    private lateinit var paddle: Paddle
    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/space.png")
        BaseActor.setWorldBounds(background)

        paddle = Paddle(320f, 32f, mainStage)

        Wall(0f, 0f, mainStage, 20f, 600f) // left wall
        Wall(780f, 0f, mainStage, 20f, 600f) // right wall
        Wall(0f, 550f, mainStage, 800f, 50f) // top wall

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
    }

    override fun update(dt: Float) {
        val mouseX = Gdx.input.x
        paddle.x = mouseX - paddle.width/2
        paddle.boundToWorld()
    }
}
