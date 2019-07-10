package chapter7

import com.badlogic.gdx.Input.Keys

class LevelScreen : BaseScreen() {
    private lateinit var plane: Plane

    override fun initialize() {
        Sky(0f, 0f, mainStage)
        Sky(800f, 0f, mainStage)
        Ground(0f, 0f, mainStage)
        Ground(800f, 0f, mainStage)

        plane = Plane(100f, 500f, mainStage)
        BaseActor.setWorldBounds(800f, 600f)
    }

    override fun update(dt: Float) {}

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.SPACE) {
            plane.boost()
        }

        return false
    }
}
