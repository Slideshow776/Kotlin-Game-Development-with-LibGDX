package chapter11

import com.badlogic.gdx.Gdx
import kotlin.math.abs
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table

class LevelScreen : BaseScreen() {
    private lateinit var jack: Koala

    private var gameOver = false
    private var coins = 0
    private var time = 60f
    private lateinit var coinLabel: Label
    private lateinit var keyTable: Table
    private lateinit var timeLabel: Label
    private lateinit var messageLabel: Label
    private lateinit var keyList: ArrayList<Color>

    override fun initialize() {
        val tma = TilemapActor("assets/map.tmx", mainStage)

        for (obj in tma.getRectangleList("solid")) {
            val props = obj.properties
            Solid(
                props.get("x") as Float,
                props.get("y") as Float,
                props.get("width") as Float,
                props.get("height") as Float,
                mainStage
                )
        }

        val startPoint = tma.getRectangleList("start")[0]
        val startProps = startPoint.properties
        jack = Koala(startProps.get("x") as Float, startProps.get("y") as Float, mainStage)

        for (obj in tma.getTileList("flag")) {
            val props = obj.properties
            Flag(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("coin")) {
            val props = obj.properties
            Coin(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("timer")) {
            val props = obj.properties
            Timer(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("springboard")) {
            val props = obj.properties
            Springboard(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("platform")) {
            val props = obj.properties
            Platform(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("key")) {
            val props = obj.properties
            val key = Key(props.get("x") as Float, props.get("y") as Float, mainStage)
            when (props.get("color") as String) {
                "red" -> key.color = Color.RED
                else -> key.color = Color.WHITE
            }
        }

        for (obj in tma.getTileList("lock")) {
            val props = obj.properties
            val lock = Lock(props.get("x") as Float, props.get("y") as Float, mainStage)
            when (props.get("color") as String) {
                "red" -> lock.color = Color.RED
                else -> lock.color = Color.WHITE
            }
        }

        jack.toFront() // causes Jack the Koala to appear in front of everything added so far

        keyList = ArrayList()

        coinLabel = Label("Coins: $coins", BaseGame.labelStyle)
        coinLabel.color = Color.GOLD
        keyTable = Table()
        timeLabel = Label("Time: ${time.toInt()}", BaseGame.labelStyle)
        timeLabel.color = Color.LIGHT_GRAY
        messageLabel = Label("Message", BaseGame.labelStyle)
        messageLabel.isVisible = false

        uiTable.pad(20f)
        uiTable.add(coinLabel)
        uiTable.add(keyTable).expandX()
        uiTable.add(timeLabel)
        uiTable.add().row()
        uiTable.add(messageLabel).colspan(3).expandY()
    }

    override fun update(dt: Float) {
        if (gameOver)
            return

        for (flag in BaseActor.getList(mainStage, Flag::class.java.canonicalName)) {
            if (jack.overlaps(flag)) {
                messageLabel.setText("You Win!")
                messageLabel.color = Color.LIME
                messageLabel.isVisible = true
                jack.remove()
                gameOver = true
            }
        }

        for (actor in BaseActor.getList(mainStage, Solid::class.java.canonicalName)) {
            val solid = actor as Solid

            if (solid is Platform) {
                if (jack.isJumping() && jack.overlaps(solid))
                    solid.enabled = false

                if (jack.isJumping() && !jack.overlaps(solid))
                    solid.enabled = true

                if (jack.isFalling() && !jack.overlaps(solid) && !jack.belowOverlaps(solid))
                    solid.enabled = true
            }

            if (solid is Lock && jack.overlaps(solid)) {
                if (keyList.contains(solid.color)) {
                    solid.enabled = false
                    solid.addAction(Actions.fadeOut(.5f))
                    solid.addAction(Actions.after(Actions.removeActor()))
                }
            }

            if (jack.overlaps(solid) && solid.enabled) {
                val offset = jack.preventOverlap(solid)

                if (offset != null) {
                    // collision in X direction
                    if (abs(offset.x) > abs(offset.y))
                        jack.velocityVec.x = 0f
                    else
                        jack.velocityVec.y = 0f
                }
            }
        }

        for ( coin in BaseActor.getList(mainStage, Coin::class.java.canonicalName)) {
            if (jack.overlaps(coin)) {
                coins++
                coinLabel.setText("Coins: $coins")
                coin.remove()
            }
        }

        time -= dt
        timeLabel.setText("Time: ${time.toInt()}")

        for (timer in BaseActor.getList(mainStage, Timer::class.java.canonicalName)) {
            if (jack.overlaps(timer)) {
                time += 20
                timer.remove()
            }
        }

        if (time <= 0) {
            messageLabel.setText("Time Up - Game Over")
            messageLabel.color = Color.RED
            messageLabel.isVisible = true
            jack.remove()
            gameOver = true
        }

        for (springboard in BaseActor.getList(mainStage, Springboard::class.java.canonicalName)) {
            if (jack.belowOverlaps(springboard) && jack.isFalling()) {
                jack.spring()
            }
        }

        for (key in BaseActor.getList(mainStage, Key::class.java.canonicalName)) {
            if (jack.overlaps(key)) {
                val keyColor = key.color
                key.remove()
                val keyIcon = BaseActor(0f, 0f, uiStage)
                keyIcon.loadTexture("assets/key-icon.png")
                keyIcon.color = keyColor
                keyTable.add(keyIcon)
                keyList.add(keyColor)
            }
        }
    }

    override fun keyDown(keyCode: Int): Boolean {
        if (gameOver)
            return false

        if (keyCode == Keys.SPACE) {
            if (Gdx.input.isKeyPressed(Keys.S)) {
                for (actor in BaseActor.getList(mainStage, Platform::class.java.canonicalName)) {
                    val platform = actor as Platform
                    if (jack.belowOverlaps(platform)) {
                        platform.enabled = false
                    }
                }
            } else if (jack.isOnSolid()) {
                jack.jump()
            }
        }
        return false
    }
}
