package chapter4

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class LevelScreen : BaseScreen() {

    private lateinit var spaceship: Spaceship

    private var ufoSpawnTimer = 0f
    private var ufoSpawnFrequence = MathUtils.random(15f)+15f

    private var powerUpSpawnTimer = 0f
    private var powerUpSpawnFrequence = MathUtils.random(15f)+15f

    private var gameOver = false

    override fun initialize() {
        var space = BaseActor(0f, 0f, mainStage)
        space.loadTexture("assets/space.png")
        space.setSize(800f, 600f)

        BaseActor.setWorldBounds(space)

        spaceship = Spaceship(400f, 300f, mainStage)

        Rock(600f, 500f, mainStage, 1.5f, 5f)
        Rock(600f, 300f, mainStage, 1.5f, 5f)
        Rock(600f, 100f, mainStage, 1.5f, 5f)
        Rock(400f, 100f, mainStage, 1.5f, 5f)
        Rock(200f, 100f, mainStage, 1.5f, 5f)
        /*Rock(200f, 300f, mainStage, 1.5f, 5f)
        Rock(200f, 500f, mainStage, 1.5f, 5f)*/
    }

    override fun update(dt: Float) {
        for (rockActor: BaseActor in BaseActor.getList(mainStage, Rock::class.java.canonicalName)) {
            if (rockActor.overlaps(spaceship)) {
                if (spaceship.shieldPower <= 0) {
                    var boom = Explosion(0f, 0f, mainStage)
                    boom.centerAtActor(spaceship)
                    spaceship.remove()
                    spaceship.setPosition(-1000f, -1000f)

                    gameOver(false)
                } else {
                    spaceship.shieldPower -=34
                    var boom = Explosion(0f, 0f, mainStage)
                    boom.centerAtActor(rockActor)

                    if (rockActor.scaleX >= 1.5f * Constants.scale)
                        spawnRocks(rockActor, 1f, 60f)
                    else if (rockActor.scaleX >= 1f * Constants.scale)
                        spawnRocks(rockActor, .5f, 200f)
                    rockActor.remove()
                }
            }
            for (laserActor: BaseActor in BaseActor.getList(mainStage, Laser::class.java.canonicalName)) {
                if (laserActor.overlaps(rockActor)) {
                    var boom = Explosion(0f, 0f, mainStage)
                    boom.centerAtActor(rockActor)
                    laserActor.remove()

                    if (rockActor.scaleX >= 1.5f * Constants.scale)
                        spawnRocks(rockActor, 1f, 60f)
                    else if (rockActor.scaleX >= 1f * Constants.scale)
                        spawnRocks(rockActor, .5f, 200f)
                    rockActor.remove()
                }
            }
        }

        for (ufoActor: BaseActor in BaseActor.getList(mainStage, Ufo::class.java.canonicalName)) {
            if (!gameOver && ufoActor.overlaps(spaceship)) {
                var boom = Explosion(0f, 0f, mainStage)
                boom.centerAtActor(spaceship)
                spaceship.remove()
                spaceship.setPosition(-1000f, -1000f)

                gameOver(false)
            }
        }

        for (powerUpActor: BaseActor in BaseActor.getList(mainStage, PowerUp::class.java.canonicalName)) {
            if (powerUpActor.overlaps(spaceship)) {
                spaceship.shieldPower = 100
                powerUpActor.remove()
            }
        }

        if (!gameOver && BaseActor.count(mainStage, Rock::class.java.canonicalName) == 0) {
            gameOver(true)
        }

        if (ufoSpawnTimer >= ufoSpawnFrequence) {
            spawnUfo()
            ufoSpawnTimer = 0f
        }
        else {
            ufoSpawnTimer += dt
        }

        if (powerUpSpawnTimer >= powerUpSpawnFrequence) {
            spawnPowerUp()
            powerUpSpawnTimer = 0f
        }
        else {
            powerUpSpawnTimer += dt
        }
    }

    // Override default InputProcessor method
    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.X)
            spaceship.warp()
        if (keycode == Keys.SPACE)
            spaceship.shoot()
        return false
    }

    private fun spawnRocks(rockActor: Actor, scale: Float, speed: Float) {
        Rock(rockActor.x, rockActor.y, mainStage, scale, speed)
        Rock(rockActor.x, rockActor.y, mainStage, scale, speed)
        Rock(rockActor.x, rockActor.y, mainStage, scale, speed)
    }

    /*
    * https://stackoverflow.com/questions/9879258/how-can-i-generate-random-points-on-a-circles-circumference-in-javascript
    * Screen is (800, 600), needed to offset the center of the spawning circle/ellipse by (400, 300)
    * */
    private fun spawnUfo() {
        val randomAngle = MathUtils.random()*MathUtils.PI2
        val randomX = MathUtils.cos(randomAngle)*810f + 400f
        val randomY = MathUtils.sin(randomAngle)*610f + 300f
        Ufo(randomX, randomY, mainStage)
    }

    private fun spawnPowerUp() {
        val x = MathUtils.random(750f)
        val y = MathUtils.random(550f)
        PowerUp(x, y, mainStage)
    }

    private fun gameOver(win: Boolean) {
        if (win) {
            var messageWin = BaseActor(0f, 0f, uiStage)
            messageWin.loadTexture("assets/message-win.png")
            messageWin.centerAtPosition(400f, 300f)
            messageWin.setOpacity(0f)
            messageWin.addAction(Actions.fadeIn(1f))
            gameOver = true
        }
        else {
            var messageLose = BaseActor(0f, 0f, uiStage)
            messageLose.loadTexture("assets/message-lose.png")
            messageLose.centerAtPosition(400f, 300f)
            messageLose.setOpacity(0f)
            messageLose.addAction(Actions.fadeIn(1f))
            gameOver = true
        }
    }
}
