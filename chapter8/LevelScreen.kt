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
    }

    override fun update(dt: Float) {
        val mouseX = Gdx.input.x
        paddle.x = mouseX - paddle.width/2
        paddle.boundToWorld()
    }
}
