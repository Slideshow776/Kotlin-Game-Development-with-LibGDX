package chapter14

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LevelScreen : BaseScreen() {
    private lateinit var maze: Maze
    private lateinit var hero: Hero
    private lateinit var heroIcon: HeroIcon

    lateinit var coinsLabel: Label
    lateinit var messageLabel: Label
    lateinit var timeLabel: Label

    var time = 0f
    var gameOver = false

    lateinit var coinSound: Sound
    lateinit var hurtSound: Sound
    lateinit var winSound: Sound
    lateinit var looseSound: Sound
    lateinit var powerupSound: Sound
    lateinit var windMusic: Music

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/white.png")
        background.color = Color.GRAY
        background.setSize(768f, 700f)

        maze = Maze(mainStage)
        BaseActor.setWorldBounds(
            (maze.roomCountX * maze.roomWidth).toFloat(),
            (maze.roomCountY * maze.roomHeight).toFloat()
        )

        hero = Hero(0f, 0f, mainStage)
        hero.centerAtActor(maze.getRoom(0, 0)!!)

        val ghost0 = Ghost(0f, 0f, mainStage)
        ghost0.centerAtActor(maze.getRoom(maze.roomCountX - 1, maze.roomCountY - 1)!!)
        ghost0.color = Color(209f / 255, 117f / 255, 117f / 255, 1f) // red normalized -> rgba(209, 117, 117, 1)
        val ghost1 = Ghost(0f, 0f, mainStage)
        ghost1.centerAtActor(maze.getRoom(9, maze.roomCountY - 1)!!)
        ghost1.color = Color(142f / 255, 209f / 255, 117f / 255, 1f) // green normalized -> rgba(142, 209, 117, 1)

        for (room in BaseActor.getList(mainStage, Room::class.java.canonicalName)) {
            if (MathUtils.random() >= .6f) { // P(maze with no Coins) = .4^(10*12) = 1.76*10^-48
                val coin = Coin(0f, 0f, mainStage)
                coin.centerAtActor(room)
            }
        }

        ghost0.toFront()
        ghost1.toFront()

        heroIcon = HeroIcon(0f, 0f, uiStage)
        heroIcon.setSize(45f, 45f)

        val timeIcon = BaseActor(0f, 0f, uiStage)
        timeIcon.loadTexture("assets/timer.png")

        coinsLabel = Label("Coins left:", BaseGame.labelStyle)
        coinsLabel.color = Color.GOLD
        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.setFontScale(2f)
        messageLabel.isVisible = false
        timeLabel = Label("Time: ${time.toInt()}", BaseGame.labelStyle)
        timeLabel.color = Color.LIGHT_GRAY

        // uiTable.debug()
        uiTable.pad(10f)
        uiTable.add(heroIcon)
        uiTable.add(timeIcon).padLeft(20f)
        uiTable.add(timeLabel)
        uiTable.add(coinsLabel).padLeft(20f)
        uiTable.row()
        uiTable.add(messageLabel).colspan(4).expandY()

        coinSound = Gdx.audio.newSound(Gdx.files.internal("assets/coin.wav"))
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("assets/hitHurt.wav"))
        winSound = Gdx.audio.newSound(Gdx.files.internal("assets/trumpetTriumph.mp3"))
        looseSound = Gdx.audio.newSound(Gdx.files.internal("assets/playerDeath.wav"))
        powerupSound = Gdx.audio.newSound(Gdx.files.internal("assets/powerup.wav"))
        windMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/wind.mp3"))
        windMusic.isLooping = true
        windMusic.volume = .1f
        windMusic.play()
    }

    override fun update(dt: Float) {
        if (gameOver)
            return

        for (wall in BaseActor.getList(mainStage, Wall::class.java.canonicalName)) {
            hero.preventOverlap(wall)
        }

        for (ghost in BaseActor.getList(mainStage, Ghost::class.java.canonicalName)) {

            ghost as Ghost

            if (ghost.actions.size == 0) {
                maze.resetRooms()
                ghost.findPath(maze.getRoom(ghost)!!, maze.getRoom(hero)!!)
            }

            for (coin in BaseActor.getList(mainStage, Coin::class.java.canonicalName)) {
                if (hero.overlaps(coin)) {
                    coin.remove()
                    coinSound.play()
                }
            }

            val coins = BaseActor.count(mainStage, Coin::class.java.canonicalName)
            coinsLabel.setText("Coins left: $coins")

            time += dt
            timeLabel.setText("Time: ${time.toInt()}")

            if (time > 20 && ghost.actorSpeed <= (hero.getSpeed() - 20))
                ghost.actorSpeed += dt

            if (coins == 0) {
                ghost.remove()
                ghost.setPosition(-1000f, -1000f)
                ghost.clearActions()
                ghost.addAction(Actions.forever(Actions.delay(1f)))
                messageLabel.setText("You win!")
                messageLabel.color = Color.GREEN
                messageLabel.isVisible = true
                winSound.play()
                gameOver = true
            }

            if (hero.overlaps(ghost) && !hero.invincible) {
                hero.hit()
                heroIcon.setAnimation(heroIcon.dying)
                heroIcon.setSize(45f, 45f)

                if (hero.health <= 0) {
                    heroIcon.setAnimation(heroIcon.dead)
                    heroIcon.setSize(45f, 45f)
                    hurtSound.play()

                    val newGhost = Ghost(hero.x, hero.y, mainStage)
                    newGhost.isVisible = false
                    newGhost.addAction(Actions.parallel(
                        Actions.fadeOut(0f),
                        Actions.fadeIn(3f),
                        Actions.scaleTo(0f, 0f),
                        Actions.scaleTo(1f, 1f, 3f),
                        Actions.run { newGhost.isVisible = true }
                    ))

                    hero.remove()
                    hero.setPosition(-1000f, -1000f)
                    ghost.addAction(Actions.forever(Actions.delay(1f)))
                    messageLabel.setText("Game Over")
                    messageLabel.color = Color.RED
                    messageLabel.isVisible = true
                    looseSound.play()
                    gameOver = true
                }
            }

            if (!messageLabel.isVisible) {
                val distance = Vector2(hero.x - ghost.x, hero.y - ghost.y).len()
                var volume = -(distance - 64) / (300 - 64) + 1
                volume = MathUtils.clamp(volume, .1f, 1f)
                windMusic.volume = volume
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.R) {
            powerupSound.play(.125f)
            dispose()
            BaseGame.setActiveScreen(LevelScreen())
        }
        return false
    }

    override fun dispose() {
        super.dispose()
        windMusic.dispose()
        coinSound.dispose()
    }
}
