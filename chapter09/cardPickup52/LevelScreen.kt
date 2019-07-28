package chapter09.cardPickup52

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class LevelScreen : BaseScreen() {
    private lateinit var pileList: ArrayList<Pile>
    private lateinit var messageLabel: Label

    private var playOnce: Boolean = true
    private lateinit var trumpetSound: Sound
    private lateinit var restartButton: Button

    private lateinit var timeLabel: Label
    private var time = 0f

    private lateinit var highscoreLabel: Label

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/felt.jpg")
        BaseActor.setWorldBounds(background)
        for (r in 0 until Card.rankNames.size) {
            for (s in 0 until Card.suitNames.size) {
                val x = MathUtils.random(0f, 800f)
                val y = MathUtils.random(0f, 300f)
                val c = Card(x, y, mainStage)
                c.setRankSuitValues(r, s)
                c.toBack()
            }
        }
        background.toBack()

        pileList = ArrayList()
        for (i in 0 until 4) {
            val pileX = 120f + 150f * i
            val pileY = 450f
            val pile = Pile(pileX, pileY, mainStage)
            pileList.add(pile)
        }

        for (actor: BaseActor in BaseActor.getList(mainStage, Card::class.java.canonicalName)) {
            val card = actor as Card
            if (card.rankValue == 0) {
                val pile = pileList[card.suitValue]
                card.toFront()
                card.moveToActor(pile)
                pile.addCard(card)
                card.isDraggable = false
            } else {
                // animation
                val randomDelay = MathUtils.random(0f, 1f)
                card.addAction(
                    Actions.sequence(
                        Actions.moveBy(0f, 620f, 0f),
                        Actions.delay(randomDelay),
                        Actions.moveTo(card.x, card.y, 2f, Interpolation.bounceOut)
                    ))
            }
        }

        val buttonStyle = Button.ButtonStyle()
        buttonStyle.up = TextureRegionDrawable(
            TextureRegion(
                Texture(Gdx.files.internal("assets/undo.png"))
            )
        )
        restartButton = Button(buttonStyle)
        restartButton.color = Color.CYAN
        restartButton.addListener { e: Event ->
            if (isTouchDownEvent(e))
                BaseGame.setActiveScreen(LevelScreen())
            false
        }
        restartButton.isVisible = false

        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.color = Color.CYAN
        messageLabel.isVisible = false

        timeLabel = Label("Time: $time", BaseGame.labelStyle)
        timeLabel.color = Color.CORAL

        highscoreLabel = Label("Best: ${BaseGame.highscore}", BaseGame.labelStyle)
        highscoreLabel.color = Color.ORANGE

        uiTable.add(timeLabel).left().pad(10f)
        uiTable.row()
        uiTable.add(highscoreLabel).left().pad(10f)
        uiTable.row()
        uiTable.add(messageLabel).expandX().expandY().bottom().pad(50f)
        uiTable.row()
        uiTable.add(restartButton).padBottom(10f)

        ScreenTransition(0f, 0f, uiStage)
        trumpetSound = Gdx.audio.newSound(Gdx.files.internal("assets/trumpet.mp3"))
    }

    override fun update(dt: Float) {
        timeLabel.setText("Time: ${MathUtils.floor(time)}")
        highscoreLabel.setText("Best: ${BaseGame.highscore}")
        var complete = true
        for (pile: Pile in pileList) {
            if (pile.getSize() < 13) {
                complete = false
            }
        }

        if (complete) {
            messageLabel.setText("You win")
            messageLabel.isVisible = true
            restartButton.isVisible = true
            BaseGame.writeHighScore(MathUtils.floor(time))
            if (playOnce) {
                playOnce = false
                trumpetSound.play()
            }
        } else {
            time += dt
        }
    }
}
