package chapter09.crazyEights

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
    private lateinit var playerHandList: ArrayList<Pile>
    private lateinit var deckList: ArrayList<Card>
    private lateinit var discardPile: Pile

    private lateinit var messageLabel: Label

    private var playOnce: Boolean = true
    private lateinit var trumpetSound: Sound
    private lateinit var restartButton: Button

    private lateinit var timeLabel: Label
    private var time = 0f

    private lateinit var highscoreLabel: Label

    override fun initialize() {
        // Create cards
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/felt.jpg")
        deckList = ArrayList()
        for (r in 0 until Card.rankNames.size) {
            for (s in 0 until Card.suitNames.size) {
                val c = Card(362f, -100f, mainStage)
                c.setRankSuitValues(r, s)
                deckList.add(c)
            }
        }

        // initialize first card
        discardPile = Pile(357f, 290f, mainStage)
        val firstCard = deckList.removeAt(MathUtils.random(0, deckList.size - 1))
        firstCard.toFront()
        firstCard.addAction(Actions.sequence(
            Actions.moveTo(362f, 620f, 0f),
            Actions.delay(2f),
            Actions.run { firstCard.moveToActor(discardPile, 1f, Interpolation.bounceOut) }
        ))
        discardPile.addCard(firstCard)
        firstCard.isDraggable = false

        // create player hand
        playerHandList = ArrayList()
        for (i in 0 until 8) {
            val pileX = 60f + 85f * i
            val pileY = 50f
            val pile = Pile(pileX, pileY, mainStage)
            pile.isDroppable = false
            pile.isVisible = false
            playerHandList.add(pile)
            pile.addAction(Actions.sequence(
                Actions.delay(3f),
                Actions.run { addCard(pile) }
            ))
        }

        // UI
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

        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.color = Color.CYAN
        messageLabel.isVisible = false

        timeLabel = Label("Time: $time", BaseGame.labelStyle)
        timeLabel.color = Color.CORAL

        highscoreLabel = Label("Best: ${BaseGame.highscore}", BaseGame.labelStyle)
        highscoreLabel.color = Color.ORANGE

        /*uiTable.debug()*/
        uiTable.add(timeLabel).left().padTop(10f).padLeft(10f)
        uiTable.row()
        uiTable.add(highscoreLabel).left().padLeft(10f)
        uiTable.add(restartButton).right().padRight(10f)
        uiTable.row()
        uiTable.add(messageLabel).expandY().expandX().colspan(2).pad(50f)

        ScreenTransition(0f, 0f, uiStage)
        trumpetSound = Gdx.audio.newSound(Gdx.files.internal("assets/trumpet.mp3"))
    }

    override fun update(dt: Float) {
        timeLabel.setText("Time: ${MathUtils.floor(time)}")
        highscoreLabel.setText("Best: ${BaseGame.highscore}")

        var complete = false
        if (time > 3f) {
            checkHand()
        }


        var count = 8
        for (pile: Pile in playerHandList) {
            if (pile.getSize() == 0) {
                count--
            }
        }

        if (count == 0 && deckList.size == 0) {
            complete = true
        }

        if (complete) {
            messageLabel.setText("You win")
            messageLabel.isVisible = true
            BaseGame.writeHighScore(MathUtils.floor(time))
            if (playOnce) {
                playOnce = false
                trumpetSound.play()
            }
        } else {
            time += dt
        }
    }

    private fun checkHand() {
        for (pile in playerHandList) {
            if (pile.getSize() == 0) {
                addCard(pile)
            }
        }
    }

    private fun addCard(pile: Pile) {
        if (deckList.size > 0) {
            val card = deckList.removeAt(MathUtils.random(0, deckList.size - 1))
            card.toFront()
            card.addAction(Actions.run { card.moveToActor(pile, 1f) })
            card.pile = pile
            pile.addCard(card)
        }
    }
}
