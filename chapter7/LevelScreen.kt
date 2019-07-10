package chapter7

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LevelScreen : BaseScreen() {
    private lateinit var plane: Plane
    private var starTimer = 0f
    private var starSpawnInterval = 4f
    private var score = 0
    private lateinit var scoreLabel: Label

    override fun initialize() {
        Sky(0f, 0f, mainStage)
        Sky(800f, 0f, mainStage)
        Ground(0f, 0f, mainStage)
        Ground(800f, 0f, mainStage)

        plane = Plane(100f, 500f, mainStage)
        BaseActor.setWorldBounds(800f, 600f)

        scoreLabel = Label("$score", BaseGame.labelStyle)

        uiTable.pad(10f)
        uiTable.add(scoreLabel)
        uiTable.row()
        uiTable.add().expandY()
    }

    override fun update(dt: Float) {
        starTimer += dt
        if (starTimer > starSpawnInterval) {
            Stars(800f, MathUtils.random(100f, 500f), mainStage)
            starTimer = 0f
        }

        for (star: BaseActor in BaseActor.getList(mainStage, Stars::class.java.canonicalName)) {
            if (plane.overlaps(star)) {
                star.remove()
                score++
                scoreLabel.setText("$score")
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.SPACE) {
            plane.boost()
        }

        return false
    }
}
