package chapter13.spaceRocksTouchscreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class LevelScreen : BaseTouchScreen() {

    private lateinit var spaceship: Spaceship

    private var ufoSpawnTimer = 0f
    private var ufoSpawnFrequence = MathUtils.random(15f)+15f

    private var powerUpSpawnTimer = 0f
    private var powerUpSpawnFrequence = MathUtils.random(15f)+15f

    private var gameOver = false

    private lateinit var music: Music
    private lateinit var winSound: Sound
    private lateinit var explosionSound: Sound
    private lateinit var shieldSound: Sound
    private lateinit var gameOverSound: Sound
    private lateinit var warpSound: Sound
    private lateinit var ufoSound: Sound
    private lateinit var shootSound: Sound
    private lateinit var shieldHitSound: Sound

    private lateinit var touchpad: Touchpad

    override fun initialize() {
        val space = BaseActor(0f, 0f, mainStage)
        space.loadTexture("assets/space.png")
        space.setSize(800f, 600f)

        BaseActor.setWorldBounds(space)

        spaceship = Spaceship(400f, 300f, mainStage)

        /*Rock(600f, 500f, mainStage, 1.5f, 5f)
        Rock(600f, 300f, mainStage, 1.5f, 5f)
        Rock(600f, 100f, mainStage, 1.5f, 5f)
        Rock(400f, 100f, mainStage, 1.5f, 5f)
        Rock(200f, 100f, mainStage, 1.5f, 5f)*/
        /*Rock(200f, 300f, mainStage, 1.5f, 5f)
        Rock(200f, 500f, mainStage, 1.5f, 5f)*/

        winSound = Gdx.audio.newSound(Gdx.files.internal("assets/congratulations.wav"))
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/explosion.wav"))
        shieldSound = Gdx.audio.newSound(Gdx.files.internal("assets/shield.wav"))
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("assets/game-over.wav"))
        warpSound = Gdx.audio.newSound(Gdx.files.internal("assets/warp.wav"))
        ufoSound = Gdx.audio.newSound(Gdx.files.internal("assets/ufo.wav"))
        shootSound = Gdx.audio.newSound(Gdx.files.internal("assets/shoot.wav"))
        shieldHitSound = Gdx.audio.newSound(Gdx.files.internal("assets/shieldHit.wav"))

        music = Gdx.audio.newMusic(Gdx.files.internal("assets/epic-retro-synth-music.wav"))
        music.isLooping = true
        music.volume = .75f
        music.play()

        Gdx.graphics.setWindowedMode(800, 800)
        initializeControlArea()
        val controlBackground = BaseActor(0f, 0f, controlStage)
        controlBackground.loadTexture("assets/pixels.jpg")

        // touchpad
        val touchStyle = TouchpadStyle()

        val padKnobTex = Texture(Gdx.files.internal("assets/joystick-knob.png"))
        val padKnobReg = TextureRegion(padKnobTex)
        touchStyle.knob = TextureRegionDrawable(padKnobReg)

        val padBackTex = Texture(Gdx.files.internal("assets/joystick-background.png"))
        val padBackReg = TextureRegion(padBackTex)
        touchStyle.background = TextureRegionDrawable(padBackReg)

        touchpad = Touchpad(5f, touchStyle)

        // buttons
        val buttonStyle = ButtonStyle()
        buttonStyle.up = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("assets/nes_button.png"))))

        val restartButton = Button(buttonStyle)
        restartButton.color = Color.BROWN
        restartButton.addListener { e: Event ->
            if(isTouchDownEvent(e)) {
                dispose()
                BaseGame.setActiveScreen(LevelScreen())
            }
            false
        }

        val shootButton = Button(buttonStyle)
        shootButton.color = Color.WHITE
        shootButton.addListener { e: Event ->
            if(isTouchDownEvent(e)) {
                spaceship.shoot()
                shootSound.play()
            }
            false
        }

        val warpButton = Button(buttonStyle)
        warpButton.color = Color.VIOLET
        warpButton.addListener { e: Event ->
            if(isTouchDownEvent(e)) {
                spaceship.warp()
                warpSound.play()
            }
            false
        }

        val moveButton = Button(buttonStyle)
        moveButton.color = Color.GREEN
        moveButton.addListener { e: Event ->
            if(isTouchDownEvent(e)) {
                spaceship.move(true)
            }
            false
        }

        //table
        controlTable.toFront()
        controlTable.pad(50f)
        controlTable.add().colspan(3).height(600f)
        controlTable.row()
        controlTable.add(touchpad)
        controlTable.add(moveButton)
        controlTable.add(shootButton)
        controlTable.add(warpButton)
        controlTable.add(restartButton)
    }

    override fun update(dt: Float) {
        for (rockActor: BaseActor in BaseActor.getList(mainStage, Rock::class.java.canonicalName)) {
            if (rockActor.overlaps(spaceship)) {
                if (spaceship.shieldPower <= 0) {
                    val boom = Explosion(0f, 0f, mainStage)
                    boom.centerAtActor(spaceship)
                    spaceship.remove()
                    spaceship.setPosition(-1000f, -1000f)
                    explosionSound.play()

                    gameOver(false)
                } else {
                    spaceship.shieldPower -=34
                    val boom = Explosion(0f, 0f, mainStage)
                    boom.centerAtActor(rockActor)

                    if (rockActor.scaleX >= 1.5f * Constants.scale)
                        spawnRocks(rockActor, 1f, 60f)
                    else if (rockActor.scaleX >= 1f * Constants.scale)
                        spawnRocks(rockActor, .5f, 200f)
                    rockActor.remove()
                    explosionSound.play()
                    shieldHitSound.play()
                }
            }
            for (laserActor: BaseActor in BaseActor.getList(mainStage, Laser::class.java.canonicalName)) {
                if (laserActor.overlaps(rockActor)) {
                    val boom = Explosion(0f, 0f, mainStage)
                    boom.centerAtActor(rockActor)
                    laserActor.remove()

                    if (rockActor.scaleX >= 1.5f * Constants.scale)
                        spawnRocks(rockActor, 1f, 60f)
                    else if (rockActor.scaleX >= 1f * Constants.scale)
                        spawnRocks(rockActor, .5f, 200f)
                    rockActor.remove()
                    explosionSound.play()
                }
            }
        }

        for (ufoActor: BaseActor in BaseActor.getList(mainStage, Ufo::class.java.canonicalName)) {
            if (!gameOver && ufoActor.overlaps(spaceship)) {
                val boom = Explosion(0f, 0f, mainStage)
                boom.centerAtActor(spaceship)
                spaceship.remove()
                spaceship.setPosition(-1000f, -1000f)
                explosionSound.play()

                gameOver(false)
            }
        }

        for (powerUpActor: BaseActor in BaseActor.getList(mainStage, PowerUp::class.java.canonicalName)) {
            if (powerUpActor.overlaps(spaceship)) {
                spaceship.shieldPower = 100
                shieldSound.play()
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

        spaceship.rotate(Vector2(touchpad.knobPercentX, touchpad.knobPercentY), dt)
        spaceship.move(false)
    }

    // Override default InputProcessor method
    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.X) {
            spaceship.warp()
            warpSound.play()
        }
        if (keycode == Keys.SPACE) {
            spaceship.shoot()
            shootSound.play()
        }
        return false
    }

    override fun dispose() {
        super.dispose()
        music.dispose()
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
        ufoSound.play()
    }

    private fun spawnPowerUp() {
        val x = MathUtils.random(750f)
        val y = MathUtils.random(550f)
        PowerUp(x, y, mainStage)
    }

    private fun gameOver(win: Boolean) {
        when (win) {
            true -> {
                val messageWin = BaseActor(0f, 0f, uiStage)
                messageWin.loadTexture("assets/message-win.png")
                messageWin.centerAtPosition(400f, 300f)
                messageWin.setOpacity(0f)
                messageWin.addAction(Actions.fadeIn(1f))
                gameOver = true
                winSound.play()
            }
            false -> {
                val messageLose = BaseActor(0f, 0f, uiStage)
                messageLose.loadTexture("assets/message-lose.png")
                messageLose.centerAtPosition(400f, 300f)
                messageLose.setOpacity(0f)
                messageLose.addAction(Actions.fadeIn(1f))
                gameOver = true
                gameOverSound.play()
            }
        }
    }
}
