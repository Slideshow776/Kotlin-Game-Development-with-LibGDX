package chapter8

import com.badlogic.gdx.Gdx

class LevelScreen : BaseScreen() {
    private lateinit var paddle: Paddle
    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/space.png")
        BaseActor.setWorldBounds(background)

        paddle = Paddle(320f, 32f, mainStage)
        paddle.boundToWorld()
    }

    override fun update(dt: Float) {
        val mouseX = Gdx.input.x
        paddle.x = mouseX - paddle.width/2
    }
}
