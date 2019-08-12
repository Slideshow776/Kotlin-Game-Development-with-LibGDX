package chapter12

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LevelScreen : BaseScreen() {
    lateinit var hero: Hero
    lateinit var sword: Sword

    var health: Int = 3
    var coins: Int = 5
    var arrows: Int = 3
    var gameOver: Boolean = false
    lateinit var healthLabel: Label
    lateinit var coinLabel: Label
    lateinit var arrowLabel: Label
    lateinit var messageLabel: Label
    lateinit var dialogBox: DialogBox

    lateinit var treasure: Treasure

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
        hero = Hero(startProps.get("x") as Float, startProps.get("y") as Float, mainStage)

        sword = Sword(0f, 0f, mainStage)
        sword.isVisible = false

        for (obj in tma.getTileList("bush")) {
            val props = obj.properties
            Bush(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        for (obj in tma.getTileList("rock")) {
            val props = obj.properties
            Rock(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        for (obj in tma.getTileList("coin")) {
            val props = obj.properties
            Coin(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        for (obj in tma.getTileList("treasure")) {
            val props = obj.properties
            treasure = Treasure(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        for (obj in tma.getTileList("flyer")) {
            val props = obj.properties
            Flyer(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }



        hero.toFront();

        healthLabel = Label(" x ", BaseGame.labelStyle)
        healthLabel.color = Color.PINK
        coinLabel = Label(" x ", BaseGame.labelStyle)
        coinLabel.color = Color.GOLD
        arrowLabel = Label(" x ", BaseGame.labelStyle)
        arrowLabel.color = Color.TAN
        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.isVisible = false

        dialogBox = DialogBox(0f, 0f, uiStage)
        dialogBox.setBackgroundColor(Color.TAN)
        dialogBox.setFontColor(Color.BROWN)
        dialogBox.setDialogSize(600f, 100f)
        dialogBox.setFontScale(.8f)
        dialogBox.alignCenter()
        dialogBox.isVisible = false

        val healthIcon = BaseActor(0f, 0f, uiStage)
        healthIcon.loadTexture("assets/heart-icon.png")
        val coinIcon = BaseActor(0f, 0f, uiStage)
        coinIcon.loadTexture("assets/coin-icon.png")
        val arrowIcon = BaseActor(0f, 0f, uiStage)
        arrowIcon.loadTexture("assets/arrow-icon.png")

        uiTable.pad(20f)
        uiTable.add(healthIcon)
        uiTable.add(healthLabel)
        uiTable.add().expandX()
        uiTable.add(coinIcon)
        uiTable.add(coinLabel)
        uiTable.add().expandX()
        uiTable.add(arrowIcon)
        uiTable.add(arrowLabel)
        uiTable.row()
        uiTable.add(messageLabel).colspan(8).expandX().expandY()
        uiTable.row()
        uiTable.add(dialogBox).colspan(8)
    }

    override fun update(dt: Float) {
        if (gameOver)
            return

        if (!sword.isVisible) {
            // hero movement controls
            if (Gdx.input.isKeyPressed(Keys.LEFT))
                hero.accelerateAtAngle(180f)
            if (Gdx.input.isKeyPressed(Keys.RIGHT))
                hero.accelerateAtAngle(0f)
            if (Gdx.input.isKeyPressed(Keys.UP))
                hero.accelerateAtAngle(90f)
            if (Gdx.input.isKeyPressed(Keys.DOWN))
                hero.accelerateAtAngle(270f)
        }

        for (solid in BaseActor.getList(mainStage, Solid::class.java.canonicalName)) {
            hero.preventOverlap(solid)

            for (flyer in BaseActor.getList(mainStage, Flyer::class.java.canonicalName)) {
                if (flyer.overlaps(solid)) {
                    flyer.preventOverlap(solid)
                    flyer.setMotionAngle(flyer.getMotionAngle() + 180f)
                }
            }
        }

        if (sword.isVisible) {
            for (bush in BaseActor.getList(mainStage, Bush::class.java.canonicalName)) {
                if (sword.overlaps(bush))
                    bush.remove()
            }

            for (flyer in BaseActor.getList(mainStage, Flyer::class.java.canonicalName)) {
                if (sword.overlaps(flyer))
                    flyer.remove()
                val coin = Coin(0f, 0f, mainStage)
                coin.centerAtActor(flyer)
                val smoke = Smoke(0f, 0f, mainStage)
                smoke.centerAtActor(flyer)
            }


        }

        healthLabel.setText(" x  $health")
        coinLabel.setText(" x  $coins")
        arrowLabel.setText(" x  $arrows")

        for (coin in BaseActor.getList(mainStage, Coin::class.java.canonicalName)) {
            if (hero.overlaps(coin)) {
                coin.remove()
                coins++
            }
        }

        if (hero.overlaps(treasure)) {
            messageLabel.setText("You win!")
            messageLabel.color = Color.LIME
            messageLabel.setFontScale(2f)
            messageLabel.isVisible = true
            treasure.remove()
            gameOver = true
        }

        if (health <= 0) {
            messageLabel.setText("Game over...")
            messageLabel.color = Color.RED
            messageLabel.setFontScale(2f)
            messageLabel.isVisible = true
            hero.remove()
            gameOver = true
        }

        for (flyer in BaseActor.getList(mainStage, Flyer::class.java.canonicalName)) {
            if (hero.overlaps(flyer)) {
                hero.preventOverlap(flyer)
                flyer.setMotionAngle(flyer.getMotionAngle() + 180f)
                val heroPosition = Vector2(hero.x, hero.y)
                val flyerPosition = Vector2(flyer.x, flyer.y)
                val hitVector = heroPosition.sub(flyerPosition)
                hero.setMotionAngle(hitVector.angle())
                hero.setSpeed(100f)
                health--
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (gameOver)
            return false

        if (keycode == Keys.S)
            swingSword()

        return false
    }

    fun swingSword() {
        // visibility determines if sword is currently swinging
        if (sword.isVisible)
            return

        hero.setSpeed(0f)

        val facingAngle = hero.facingAngle

        val offset = Vector2()
        when (facingAngle) {
            0f -> offset.set(.5f, .2f)
            90f -> offset.set(.65f, .5f)
            180f -> offset.set(.4f, .2f)
            else -> offset.set(.25f, .2f)
        }

        sword.setPosition(hero.x, hero.y)
        sword.moveBy(offset.x * hero.width, offset.y * hero.height)

        val swordArc = 90f
        sword.rotation = facingAngle - swordArc / 2
        sword.originX = 0f

        sword.isVisible = true
        sword.addAction(Actions.rotateBy(swordArc, .25f))
        sword.addAction(Actions.after(Actions.visible(false)))

        // hero should appear in front of sword when facing north or west
        if (facingAngle == 90f || facingAngle == 180f)
            hero.toFront()
        else
            sword.toFront()
    }
}
