package chapter12

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Array

class LevelScreen : BaseScreen() {
    lateinit var hero: Hero
    lateinit var sword: Sword

    var health: Int = 3
    var coins: Int = 7
    var arrows: Int = 3
    var bombs: Int = 2
    var gameOver: Boolean = false
    lateinit var healthLabel: Label
    lateinit var coinLabel: Label
    lateinit var arrowLabel: Label
    lateinit var bombLabel: Label
    lateinit var messageLabel: Label
    lateinit var dialogBox: DialogBox

    lateinit var treasure: Treasure

    lateinit var shopHeart: ShopHeart
    lateinit var shopArrow: ShopArrow
    lateinit var shopBomb: ShopBomb

    lateinit var npcAudio: Sound
    lateinit var buyAudio: Sound
    lateinit var coinPickupAudio: Sound
    lateinit var enemyDeathAudio: Sound
    lateinit var gameOverAudio: Sound
    lateinit var hitHurtAudio: Sound
    lateinit var playerDeathAudio: Sound
    lateinit var shootArrowAudio: Sound
    lateinit var swordSwooshAudio: Sound
    lateinit var thudAudio: Sound
    lateinit var bushAudio: Sound
    lateinit var trumpetTriumphAudio: Sound
    lateinit var explosionAudio: Sound
    lateinit var walkInGrassAudio: Music
    lateinit var fuseAudio: Music

    var playNpcAudioOnce: Boolean = true

    lateinit var restartButton: Button
    lateinit var gameOverTable: Table

    lateinit var focalPoints: Array<Vector2>

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

        for (obj in tma.getTileList("skull")) {
            val props = obj.properties
            Skull(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage,
                hero
            )
        }

        for (obj in tma.getTileList("npc")) {
            val props = obj.properties
            val s = NPC(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
            s.setID(props.get("id") as String)
            s.text = props.get("text") as String
        }

        for (obj in tma.getTileList("shopheart")) {
            val props = obj.properties
            shopHeart = ShopHeart(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        for (obj in tma.getTileList("shoparrow")) {
            val props = obj.properties
            shopArrow = ShopArrow(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        for (obj in tma.getTileList("shopbomb")) {
            val props = obj.properties
            shopBomb = ShopBomb(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        focalPoints = Array()
        for (obj in tma.getTileList("focalpoint")) {
            val props = obj.properties
            focalPoints.add(
                Vector2(
                    props.get("x") as Float,
                    props.get("y") as Float
                )
            )
        }

        hero.toFront();

        healthLabel = Label(" x ", BaseGame.labelStyle)
        healthLabel.color = Color.PINK
        coinLabel = Label(" x ", BaseGame.labelStyle)
        coinLabel.color = Color.GOLD
        arrowLabel = Label(" x ", BaseGame.labelStyle)
        arrowLabel.color = Color.TAN
        bombLabel = Label(" x ", BaseGame.labelStyle)
        bombLabel.color = Color.TAN
        messageLabel = Label("...", BaseGame.labelStyle)

        dialogBox = DialogBox(0f, 0f, uiStage)
        dialogBox.setBackgroundColor(Color.TAN)
        dialogBox.setFontColor(Color.BROWN)
        dialogBox.setDialogSize(600f, 120f)
        dialogBox.setFontScale(.7f)
        dialogBox.alignCenter()
        dialogBox.isVisible = false

        val healthIcon = BaseActor(0f, 0f, uiStage)
        healthIcon.loadTexture("assets/heart-icon.png")
        val coinIcon = BaseActor(0f, 0f, uiStage)
        coinIcon.loadTexture("assets/coin-icon.png")
        val arrowIcon = BaseActor(0f, 0f, uiStage)
        arrowIcon.loadTexture("assets/arrow-icon.png")
        val bombIcon = BaseActor(0f, 0f, uiStage)
        bombIcon.loadTexture("assets/bomb-icon.png")
        bombIcon.setSize(32f, 32f)

        val buttonStyle = ButtonStyle()
        buttonStyle.up = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("assets/undo.png"))))
        restartButton = Button(buttonStyle)
        restartButton.color = Color.CYAN
        restartButton.addListener { e: Event ->
            if (isTouchDownEvent(e)) {
                dispose()
                BaseGame.setActiveScreen(LevelScreen())
            }
            false
        }

        gameOverTable = Table()
        gameOverTable.isVisible = false
        gameOverTable.add(messageLabel)
        gameOverTable.row()
        gameOverTable.add(restartButton)

        // uiTable.debug = true
        uiTable.pad(20f)
        uiTable.add(healthIcon)
        uiTable.add(healthLabel)
        uiTable.add().expandX()
        uiTable.add(coinIcon)
        uiTable.add(coinLabel)
        uiTable.add().expandX()
        uiTable.add(arrowIcon)
        uiTable.add(arrowLabel)
        uiTable.add().expandX()
        uiTable.add(bombIcon)
        uiTable.add(bombLabel)
        uiTable.row()
        uiTable.add(gameOverTable).colspan(12).expandX().expandY()
        uiTable.row()
        uiTable.add(dialogBox).colspan(12)

        npcAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/bell.wav"))
        buyAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/buy.mp3"))
        coinPickupAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/coinPickup.wav"))
        enemyDeathAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/enemyDeath.wav"))
        gameOverAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/gameOver.wav"))
        hitHurtAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/hitHurt.wav"))
        playerDeathAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/playerDeath.wav"))
        shootArrowAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/shootArrow.wav"))
        swordSwooshAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/swordSwoosh.wav"))
        thudAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/thud.wav"))
        bushAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/bush.wav"))
        trumpetTriumphAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/trumpetTriumph.mp3"))
        explosionAudio = Gdx.audio.newSound(Gdx.files.internal("assets/audio/explosion.wav"))
        walkInGrassAudio = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/walkGrass.wav"))
        fuseAudio = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/fuse.wav"))

        ScreenTransition(0f, 0f, uiStage)
    }

    override fun update(dt: Float) {
        if (gameOver)
            return

        if (!sword.isVisible) {
            // hero movement controls
            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
                hero.accelerateAtAngle(180f)
                playConsecutiveAudio(walkInGrassAudio)
            }
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
                hero.accelerateAtAngle(0f)
                playConsecutiveAudio(walkInGrassAudio)
            }
            if (Gdx.input.isKeyPressed(Keys.UP)) {
                playConsecutiveAudio(walkInGrassAudio)
                hero.accelerateAtAngle(90f)
            }
            if (Gdx.input.isKeyPressed(Keys.DOWN)) {
                playConsecutiveAudio(walkInGrassAudio)
                hero.accelerateAtAngle(270f)
            }
        }

        for (solid in BaseActor.getList(mainStage, Solid::class.java.canonicalName)) {
            hero.preventOverlap(solid)

            for (flyer in BaseActor.getList(mainStage, Flyer::class.java.canonicalName)) {
                if (flyer.overlaps(solid)) {
                    flyer.preventOverlap(solid)
                    flyer.setMotionAngle(flyer.getMotionAngle() + 180f)
                }
            }

            for (skull in BaseActor.getList(mainStage, Skull::class.java.canonicalName)) {
                if (skull.overlaps(solid)) {
                    skull.preventOverlap(solid)
                    skull.setMotionAngle(skull.getMotionAngle() + 180f)
                }
            }
        }

        if (sword.isVisible) {
            for (bush in BaseActor.getList(mainStage, Bush::class.java.canonicalName)) {
                if (sword.overlaps(bush)) {
                    bushAudio.play()
                    bush.remove()
                }
            }

            for (flyer in BaseActor.getList(mainStage, Flyer::class.java.canonicalName)) {
                if (sword.overlaps(flyer)) {
                    flyer.remove()
                    val coin = Coin(0f, 0f, mainStage)
                    coin.centerAtActor(flyer)
                    val smoke = Smoke(0f, 0f, mainStage)
                    smoke.centerAtActor(flyer)
                    enemyDeathAudio.play()
                }
            }

            for (skull in BaseActor.getList(mainStage, Skull::class.java.canonicalName)) {
                if (sword.overlaps(skull)) {
                    skull.remove()
                    val coin = Coin(0f, 0f, mainStage)
                    coin.centerAtActor(skull)
                    val smoke = Smoke(0f, 0f, mainStage)
                    smoke.centerAtActor(skull)
                    enemyDeathAudio.play()
                }
            }


        }

        healthLabel.setText(" x  $health")
        coinLabel.setText(" x  $coins")
        arrowLabel.setText(" x  $arrows")
        bombLabel.setText(" x  $bombs")

        for (coin in BaseActor.getList(mainStage, Coin::class.java.canonicalName)) {
            if (hero.overlaps(coin)) {
                coin.remove()
                coins++
                coinPickupAudio.play()
            }
        }

        if (hero.overlaps(treasure)) {
            messageLabel.setText("You win!")
            messageLabel.color = Color.LIME
            messageLabel.setFontScale(2f)
            gameOverTable.isVisible = true
            treasure.remove()
            gameOver = true
            trumpetTriumphAudio.play()
        }

        if (health <= 0) {
            messageLabel.setText("Game over...")
            messageLabel.color = Color.RED
            messageLabel.setFontScale(2f)
            gameOverTable.isVisible = true
            hero.remove()
            gameOver = true
            gameOverAudio.play()
            playerDeathAudio.play()
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
                hitHurtAudio.play()
            }


        }

        for (skull in BaseActor.getList(mainStage, Skull::class.java.canonicalName)) {
            if (hero.overlaps(skull)) {
                hero.preventOverlap(skull)
                skull.setMotionAngle(skull.getMotionAngle() + 180f)
                val heroPosition = Vector2(hero.x, hero.y)
                val skullPosition = Vector2(skull.x, skull.y)
                val hitVector = heroPosition.sub(skullPosition)
                hero.addAction(Actions.moveBy(hitVector.x, hitVector.y, .1f, Interpolation.exp10))
                health--
                hitHurtAudio.play()
            }

        }

        for (arrow in BaseActor.getList(mainStage, Arrow::class.java.canonicalName)) {
            for (flyer in BaseActor.getList(mainStage, Flyer::class.java.canonicalName)) {
                if (arrow.overlaps(flyer)) {
                    flyer.remove()
                    arrow.remove()
                    val coin = Coin(0f, 0f, mainStage)
                    coin.centerAtActor(flyer)
                    val smoke = Smoke(0f, 0f, mainStage)
                    smoke.centerAtActor(flyer)
                    hitHurtAudio.play()
                }
            }

            for (skull in BaseActor.getList(mainStage, Skull::class.java.canonicalName)) {
                if (arrow.overlaps(skull)) {
                    skull.remove()
                    arrow.remove()
                    val coin = Coin(0f, 0f, mainStage)
                    coin.centerAtActor(skull)
                    val smoke = Smoke(0f, 0f, mainStage)
                    smoke.centerAtActor(skull)
                    hitHurtAudio.play()
                }
            }

            for (solid in BaseActor.getList(mainStage, Solid::class.java.canonicalName)) {
                if (arrow.overlaps(solid)) {
                    arrow.preventOverlap(solid)
                    arrow.setSpeed(0f)
                    arrow.addAction(Actions.fadeOut(.5f))
                    arrow.addAction(Actions.after(Actions.removeActor()))
                    thudAudio.play()
                }
            }
        }

        for (npcActor in BaseActor.getList(mainStage, NPC::class.java.canonicalName)) {
            val npc = npcActor as NPC
            hero.preventOverlap(npc)
            val nearby = hero.isWithinDistance(4f, npc)

            if (nearby && !npc.viewing) {
                // check NPC ID for dynamic text
                if (npc.getID() == "gatekeeper") {
                    val flyerCount = BaseActor.count(mainStage, Flyer::class.java.canonicalName)
                    val skullCount = BaseActor.count(mainStage, Skull::class.java.canonicalName)
                    var message = "Destroy the Flyers and the Skulls and you can have the treasure. "
                    when {
                        flyerCount > 1 -> message += "There are $flyerCount Flyers left and $skullCount Skulls left."
                        flyerCount == 1 -> message += "There is $flyerCount Flyers left and $skullCount Skulls left."
                        else -> { // flyerCount == 0
                            message += "It is yours!"
                            npc.addAction(Actions.fadeOut(3f))
                            npc.addAction(Actions.after(Actions.moveBy(-10_000f, -10_000f)))
                        }
                    }
                    dialogBox.setText(message)
                } else {
                    dialogBox.setText(npc.text)
                }

                dialogBox.isVisible = true
                npc.viewing = true
                if (playNpcAudioOnce) {
                    npcAudio.play()
                    playNpcAudioOnce = false
                }
            }

            if (npc.viewing && !nearby) {
                dialogBox.setText(" ")
                dialogBox.isVisible = false
                npc.viewing = false
                playNpcAudioOnce = true
            }
        }

        for (bomb in BaseActor.getList(mainStage, Bomb::class.java.canonicalName)) {
            playConsecutiveAudio(fuseAudio)
            if (bomb.isAnimationFinished()) {
                val explosion = Explosion(0f, 0f, mainStage)
                explosionAudio.play()
                explosion.centerAtActor(bomb)
                bomb.remove()
            }
        }

        for (explosion in BaseActor.getList(mainStage, Explosion::class.java.canonicalName)) {
            for (bush in BaseActor.getList(mainStage, Bush::class.java.canonicalName)) {
                if (explosion.isWithinDistance(4f, bush)) {
                    bushAudio.play()
                    bush.remove()
                }
            }

            for (flyer in BaseActor.getList(mainStage, Flyer::class.java.canonicalName)) {
                if (explosion.isWithinDistance(4f, flyer)) {
                    enemyDeathAudio.play()
                    flyer.remove()
                }
            }
            for (skull in BaseActor.getList(mainStage, Skull::class.java.canonicalName)) {
                if (explosion.isWithinDistance(4f, skull)) {
                    enemyDeathAudio.play()
                    skull.remove()
                }
            }

            if (explosion.isWithinDistance(4f, hero)) {
                health = 0
            }
        }

        // camera
        if (!hero.searchFocalPoints(focalPoints, Vector2(hero.x, hero.y), 90f, .1f)) {
            hero.alignCamera(lerp = .1f)
            for (flyer in BaseActor.getList(mainStage, Flyer::class.java.canonicalName)) {
                if (hero.isWithinDistance(200f, flyer))
                    hero.averageBetweenTargetsCamera(Vector2(hero.x, hero.y), Vector2(flyer.x, flyer.y), .1f)
            }
            for (skull in BaseActor.getList(mainStage, Skull::class.java.canonicalName)) {
                if (hero.isWithinDistance(200f, skull))
                    hero.averageBetweenTargetsCamera(Vector2(hero.x, hero.y), Vector2(skull.x, skull.y), .1f)
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (gameOver)
            return false

        if (keycode == Keys.S)
            swingSword()

        if (keycode == Keys.A)
            shootArrow()

        if (keycode == Keys.E)
            placeBomb()

        if (keycode == Keys.B) {
            if (hero.overlaps(shopHeart) && coins >= 3) {
                coins -= 3
                health += 1
                buyAudio.play()
            }

            if (hero.overlaps(shopArrow) && coins >= 4) {
                coins -= 4
                arrows += 3
                buyAudio.play()
            }

            if (hero.overlaps(shopBomb) && coins >= 5) {
                coins -= 5
                bombs += 2
                buyAudio.play()
            }
        }

        return false
    }

    fun placeBomb() {
        if (bombs <= 0)
            return

        bombs--

        val bomb = Bomb(0f, 0f, mainStage)
        bomb.centerAtActor(hero)
    }

    fun shootArrow() {
        if (arrows <= 0)
            return

        arrows--

        val arrow = Arrow(0f, 0f, mainStage)
        arrow.centerAtActor(hero)
        arrow.rotation = hero.facingAngle
        arrow.setMotionAngle(hero.facingAngle)

        shootArrowAudio.play()
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

        swordSwooshAudio.play()
    }
}
