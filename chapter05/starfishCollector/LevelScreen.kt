package chapter05.starfishCollector

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type


class LevelScreen: BaseScreen() {

    private lateinit var turtle: Turtle
    private var win: Boolean = false
    private lateinit var starfishLabel: Label
    private lateinit var dialogBox: DialogBox

    private lateinit var timeLabel: Label
    private var time = 30
    private var timeCount = 0f

    private var pause = false

    override fun initialize() {
        val ocean = BaseActor(0f, 0f, mainStage)
        ocean.loadTexture("assets/water-border.jpg")
        ocean.setSize(1200f, 900f)
        BaseActor.setWorldBounds(ocean)

        Starfish(400f, 400f, mainStage)
        Starfish(500f, 100f, mainStage)
        Starfish(100f, 450f, mainStage)
        Starfish(200f, 250f, mainStage)

        Rock(200f, 150f, mainStage)
        Rock(100f, 300f, mainStage)
        Rock(300f, 350f, mainStage)
        Rock(4500f, 200f, mainStage)

        turtle = Turtle(20f, 20f, mainStage)

        // User interface code

        starfishLabel = Label("Starfish left: ", BaseGame.labelStyle)
        starfishLabel.color = Color.CYAN
        /*starfishLabel.setPosition(20f, 520f)
        uiStage.addActor(starfishLabel)*/

        timeLabel = Label("Time: ", BaseGame.labelStyle)
        timeLabel.color = Color.CYAN

        val buttonStyle = ButtonStyle()

        val buttonTex = Texture(Gdx.files.internal("assets/undo.png"))
        val buttonRegion = TextureRegion(buttonTex)
        buttonStyle.up = TextureRegionDrawable(buttonRegion)

        val restartButton = Button(buttonStyle)
        restartButton.color = Color.CYAN
        /*restartButton.setPosition(720f,520f);
        uiStage.addActor(restartButton);*/

        restartButton.addListener { e: Event ->
            val ie = e as InputEvent
            if (ie.type == Type.touchDown)
                BaseGame.setActiveScreen(LevelScreen())
            false
        }

        val buttonStyle2 = ButtonStyle()
        buttonStyle2.up = TextureRegionDrawable(TextureRegion(Texture("assets/pause.png")))
        val pauseButton = Button(buttonStyle2)
        pauseButton.color = Color.CYAN
        pauseButton.addListener { e: Event ->
            if(e is InputEvent && e.type == Type.touchDown) {
                pause = !pause
                turtle.pause = pause
            }
            false
        }

        uiTable.pad(10f)
        uiTable.add(starfishLabel).top()
        uiTable.add().expandX().expandY()
        uiTable.add(timeLabel).top()
        uiTable.add(pauseButton).top()
        uiTable.add(restartButton).top()

        val sign1 = Sign(20f, 400f, mainStage)
        sign1.setText("West Starfish Bay")

        val sign2 = Sign(600f, 300f, mainStage)
        sign2.setText("East Starfish Bay")

        dialogBox = DialogBox(0f, 0f, mainStage)
        dialogBox.setBackgroundColor(Color.TAN)
        dialogBox.setFontColor(Color.BROWN)
        dialogBox.setDialogSize(600f, 100f)
        dialogBox.setFontScale(.8f)
        dialogBox.alignCenter()
        dialogBox.isVisible = false

        uiTable.row()
        uiTable.add(dialogBox).colspan(3)
    }

    override fun update(dt: Float) {
        if (!pause) {
            for (rockActor: BaseActor in BaseActor.getList(mainStage, Rock::class.java.canonicalName)) {
                turtle.preventOverlap(rockActor)
            }

            for (starfishActor: BaseActor in BaseActor.getList(mainStage, Starfish::class.java.canonicalName)) {
                val starfish = starfishActor as Starfish
                if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                    starfish.collect()

                    val whirl = Whirlpool(0f, 0f, mainStage)
                    whirl.centerAtActor(starfish)
                    whirl.setOpacity(.25f)
                }
            }

            if (BaseActor.count(mainStage, Starfish::class.java.canonicalName) == 0 && !win && time >= 0f) {
                win = true
                val youWinMessage = BaseActor(0f, 0f, uiStage)
                youWinMessage.loadTexture("assets/you-win.png")
                youWinMessage.centerAtPosition(400f, 300f)
                youWinMessage.setOpacity(0f)
                youWinMessage.addAction(Actions.delay(1f))
                youWinMessage.addAction(Actions.after(Actions.fadeIn(1f)))
                youWinMessage.addAction(Actions.delay(5f))
                youWinMessage.addAction(
                    Actions.after(
                        Actions.run {
                            BaseGame.setActiveScreen( StoryScreen2() )
                        }
                    )
                )
            }

            starfishLabel.setText("Starfish left: " + BaseActor.count(mainStage, Starfish::class.java.canonicalName))

            timeCount += dt
            if (timeCount >= 1f && !win) {
                timeCount = 0f
                time -= 1
            }

            if(time >= 0)
                timeLabel.setText("Time: $time")

            if (time <= 0 && !win) {
                win = false
                val gameOver = BaseActor(0f, 0f, uiStage)
                gameOver.loadTexture("assets/game-over.png")
                gameOver.centerAtPosition(400f, 300f)
                gameOver.setOpacity(0f)
                gameOver.addAction(Actions.delay(1f))
                gameOver.addAction(Actions.after(Actions.fadeIn(1f)))
                gameOver.addAction(Actions.delay(5f))
                gameOver.addAction(
                    Actions.after(
                        Actions.run {
                            BaseGame.setActiveScreen( StoryScreen3() )
                        }
                    )
                )
            }

            for (signActor: BaseActor in BaseActor.getList(mainStage, Sign::class.java.canonicalName)) {
                val sign = signActor as Sign
                turtle.preventOverlap(sign)
                val nearBy = turtle.isWithinDistance(4f, sign)

                if (nearBy && !sign.isViewing()) {
                    dialogBox.setText(sign.getText())
                    dialogBox.isVisible = true
                    sign.setViewing(true)
                }

                if (sign.isViewing() && !nearBy) {
                    dialogBox.setText(" ")
                    dialogBox.isVisible = false
                    sign.setViewing(false)
                }
            }
        }
        else {
            turtle.pause = true
        }
    }
}
