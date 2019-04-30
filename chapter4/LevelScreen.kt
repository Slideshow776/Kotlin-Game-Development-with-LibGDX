package chapter4

import com.badlogic.gdx.Input.Keys

class LevelScreen : BaseScreen() {

    lateinit var spaceship: Spaceship

    override fun initialize() {
        var space = BaseActor(0f, 0f, mainStage)
        space.loadTexture("assets/space.png")
        space.setSize(800f, 600f)

        BaseActor.setWorldBounds(space)

        spaceship = Spaceship(400f, 300f, mainStage)
    }

    override fun update(dt: Float) {}

    // Override default InputProcessor method
    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.X)
            spaceship.warp()
        if (keycode == Keys.SPACE)
            spaceship.shoot()
        return false
    }
}
