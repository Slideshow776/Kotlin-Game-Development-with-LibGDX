package chapter9.jigsawPuzzle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.compression.lzma.Base

class LevelScreen : BaseScreen() {
    private var messageLabel: Label? = null
    private lateinit var timeLabel: Label
    private lateinit var highscoreLabel: Label

    private var time = 0f

    private lateinit var backgroundMusic: Music

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/background.jpg")

        val numberRows = 4
        val numberCols = 4

        val texture = Texture(Gdx.files.internal("assets/sun.jpg"), true)
        val imageWidth = texture.width
        val imageHeight = texture.height
        val pieceWidth = imageWidth / numberCols
        val pieceHeight = imageHeight / numberRows

        val temp = TextureRegion.split(texture, pieceWidth, pieceHeight)

        for (r in 0 until numberRows) {
            for (c in 0 until numberCols) {
                // create puzzle piece at random location on left half of screen
                val pieceX = MathUtils.random(0, 400 - pieceWidth)
                val pieceY = MathUtils.random(0, 600 - pieceHeight)
                val pp = PuzzlePiece(pieceX.toFloat(), pieceY.toFloat(), mainStage)

                val anim = Animation(1f, temp[r][c])
                pp.setAnimation(anim)
                pp.row = r
                pp.col = c

                val borderX = (400 - imageWidth) / 2
                val borderY = (600 - imageHeight) / 2

                val areaX = 400 + borderX + pieceWidth * c
                val areaY = 600 - borderY - pieceHeight - pieceHeight * r

                val pa = PuzzleArea(areaX.toFloat(), areaY.toFloat(), mainStage)
                pa.setSize(pieceWidth.toFloat(), pieceHeight.toFloat())
                pa.setBoundaryRectangle()
                pa.row = r
                pa.col = c
            }
        }

        timeLabel = Label("Time: $time", BaseGame.labelStyle)
        timeLabel.color = Color.CORAL

        highscoreLabel = Label("Best: ${BaseGame.highscore}", BaseGame.labelStyle)
        highscoreLabel.color = Color.ORANGE

        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel!!.color = Color.CYAN
        messageLabel!!.isVisible = false

        uiTable.add(timeLabel).padTop(10f)
        uiTable.row()
        uiTable.add(highscoreLabel)
        uiTable.row().expandY()
        uiTable.add(messageLabel).bottom().pad(50f)

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/backgroundMusic.wav"))
        backgroundMusic.volume = .25f
        backgroundMusic.isLooping = true
        backgroundMusic.play()
    }

    override fun update(dt: Float) {
        timeLabel.setText("Time: ${MathUtils.floor(time)}")
        highscoreLabel.setText("Best: ${BaseGame.highscore}")

        // check to see if pieces are in correct positions
        var solved = true
        for (actor in BaseActor.getList(mainStage, PuzzlePiece::class.java.canonicalName)) {
            val pp = actor as PuzzlePiece

            if (!pp.isCorrectlyPlaced)
                solved = false
        }

        if (solved) {
            messageLabel!!.setText("You win!")
            messageLabel!!.isVisible = true

            BaseGame.writeHighScore(MathUtils.floor(time))
        } else {
            messageLabel!!.setText("...")
            messageLabel!!.isVisible = false
            time += dt
        }
    }
}
