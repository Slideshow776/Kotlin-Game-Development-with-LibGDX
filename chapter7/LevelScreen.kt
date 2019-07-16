package chapter7

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class LevelScreen : BaseScreen() {
    private lateinit var plane: Plane

    private var starTimer = 0f
    private var starSpawnInterval = 4f

    private var score = 0
    private lateinit var scoreLabel: Label

    private var enemyTimer = 0f
    private var enemySpeed = 100f
    private var enemySpawnInterval = 3f

    private var gameOver = false
    private lateinit var gameOverMessage: BaseActor

    private lateinit var backgroundMusic: Music
    private lateinit var sparkleSound: Sound
    private lateinit var explosionSound: Sound

    private lateinit var restartButton: TextButton

    override fun initialize() {
        /*
        Sky(0f, 0f, mainStage)
        Sky(800f, 0f, mainStage)
        Ground(0f, 0f, mainStage)
        Ground(800f, 0f, mainStage)
        */

        Parallax(800f, 0f, mainStage, "assets/clouds0.png", 10f)
        Parallax(0f, 0f, mainStage, "assets/clouds0.png", 10f)
        Parallax(800f, 0f, mainStage, "assets/clouds1.png", 25f)
        Parallax(0f, 0f, mainStage, "assets/clouds1.png", 25f)
        Parallax(800f, 0f, mainStage, "assets/mountains0.png", 35f)
        Parallax(0f, 0f, mainStage, "assets/mountains0.png", 35f)
        Parallax(800f, 0f, mainStage, "assets/mountains1.png", 45f)
        Parallax(0f, 0f, mainStage, "assets/mountains1.png", 45f)
        Ground(0f, 0f, mainStage)
        Ground(800f, 0f, mainStage)

        plane = Plane(100f, 500f, mainStage)
        BaseActor.setWorldBounds(800f, 600f)

        scoreLabel = Label("$score", BaseGame.labelStyle)

        gameOverMessage = BaseActor(0f, 0f, mainStage)
        gameOverMessage.loadTexture("assets/game-over.png")
        gameOverMessage.isVisible = false

        restartButton = TextButton("Restart", BaseGame.textButtonStyle)
        restartButton.addListener {e: Event ->
            if (isTouchDownEvent(e))
                BaseGame.setActiveScreen(MenuScreen())
            false
        }
        restartButton.isVisible = false

        // scene graph
        val messageTable = Table()
        messageTable.add(gameOverMessage)
        messageTable.row()
        messageTable.add(restartButton)

        uiTable.pad(10f)
        uiTable.add(scoreLabel).top()
        uiTable.row()
        uiTable.add(messageTable).expandY()

        sparkleSound = Gdx.audio.newSound(Gdx.files.internal("assets/sparkle.mp3"))
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/explosion.wav"))

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Prelude-and-Action.mp3"))
        backgroundMusic.isLooping = true
        backgroundMusic.volume = 1f
        backgroundMusic.play()
    }

    override fun update(dt: Float) {
        if (gameOver)
            return

        starTimer += dt
        if (starTimer > starSpawnInterval) {
            Stars(800f, MathUtils.random(100f, 500f), mainStage)
            starTimer = 0f
        }

        for (star: BaseActor in BaseActor.getList(mainStage, Stars::class.java.canonicalName)) {
            if (plane.overlaps(star)) {
                val sparkle = Sparkle(0f, 0f, mainStage)
                sparkle.centerAtActor(star)
                sparkleSound.play()
                star.remove()
                score++
                scoreLabel.setText("$score")
            }
        }

        enemyTimer += dt
        if (enemyTimer > enemySpawnInterval) {
            val enemy = Enemy(800f, MathUtils.random(100f, 500f), mainStage)
            enemy.setSpeed(enemySpeed)

            enemyTimer = 0f
            enemySpawnInterval -= .1f
            enemySpeed += 10

            if (enemySpawnInterval < .5f)
                enemySpawnInterval = .5f

            if (enemySpeed > 400f)
                enemySpeed = 400f
        }

        for (enemy: BaseActor in BaseActor.getList(mainStage, Enemy::class.java.canonicalName)) {
            if (plane.overlaps(enemy)) {
                plane.hit()
                if (plane.getHealth() <= 0) {
                    val explosion = Explosion(0f, 0f, mainStage)
                    explosion.centerAtActor(plane)
                    explosion.setScale(3f)
                    explosionSound.play()
                    backgroundMusic.stop()
                    plane.remove()
                    gameOver = true
                    restartButton.isVisible = true
                    BaseGame.writeHighScore(score)
                    gameOverMessage.isVisible = true
                    for (enemy1: BaseActor in BaseActor.getList(mainStage, Enemy::class.java.canonicalName)) {
                        enemy1 as Enemy
                        enemy1.stopMusic()
                    }
                }
            }

            if (enemy.x + enemy.width < 0) {
                score++
                scoreLabel.setText("$score")
                enemy as Enemy
                enemy.stopMusic()
                enemy.remove()
            }
        }

        if (Gdx.input.isKeyPressed(Keys.SPACE) && !gameOver) { // continuous
            plane.boost()
        }
    }

    /*override fun keyDown(keycode: Int): Boolean { // discrete
        if (keycode == Keys.SPACE && !gameOver) {
            plane.boost()
        }

        return false
    }*/
}
