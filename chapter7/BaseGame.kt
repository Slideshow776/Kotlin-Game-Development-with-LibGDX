package chapter7

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable

/**
 * Created when program is launched;
 * manages the screens that appear during the game.
 */
abstract class BaseGame : Game() {
    /**
     * Called when game is initialized; stores global reference to game object.
     */
    init {
        game = this
    }

    companion object {
        /**
         * Stores reference to game; used when calling setActiveScreen method.
         */
        private var game: BaseGame? = null

        var labelStyle: LabelStyle? = null
        var textButtonStyle: TextButtonStyle? = null
        var highscore: Int? = null

        /**
         * Used to switch screens while game is running.
         * Method is static to simplify usage.
         */
        fun setActiveScreen(s: BaseScreen) {
            game?.setScreen(s)
        }

        fun writeHighScore(highScore: Int) {
            val file = Gdx.files.local("assets/highscore.txt")
            if (highscore == null || highscore!! < highScore) {
                highscore = highScore
                file.writeString(highScore.toString(), false)
            }
        }

        private fun readFromFile(): Int {
            val file = Gdx.files.internal("assets/highscore.txt")
            return file.readString().toInt()
        }
    }

    override fun create() {
        // prepare for multiple classes/stages to receive discrete input
        val im = InputMultiplexer()
        Gdx.input.inputProcessor = im

        // assets

        // fonts
        val fontGenerator = FreeTypeFontGenerator(Gdx.files.internal("assets/OpenSans.ttf"))
        val fontParameters = FreeTypeFontParameter()
        fontParameters.size = 36
        fontParameters.color = Color.WHITE
        fontParameters.borderWidth = 2f
        fontParameters.borderColor = Color.BLACK
        fontParameters.borderStraight = true
        fontParameters.minFilter = Texture.TextureFilter.Linear
        fontParameters.magFilter = Texture.TextureFilter.Linear

        val customFont = fontGenerator.generateFont(fontParameters)

        labelStyle = LabelStyle()
        labelStyle!!.font = customFont
        /*labelStyle!!.font = BitmapFont(Gdx.files.internal("assets/cooper.fnt"))*/

        // buttons
        textButtonStyle = TextButtonStyle()
        val buttonTex = Texture(Gdx.files.internal("assets/button.png"))
        val buttonPatch = NinePatch(buttonTex, 24, 24, 24, 24)
        textButtonStyle!!.up = NinePatchDrawable(buttonPatch)
        textButtonStyle!!.font = customFont
        textButtonStyle!!.fontColor = Color.PINK

        // file
        try {
            highscore = readFromFile()
        } catch (e: Exception) {
            writeHighScore(0)
        }
    }
}
