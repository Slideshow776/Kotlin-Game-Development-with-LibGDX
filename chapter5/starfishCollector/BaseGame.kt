package chapter5.starfishCollector

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

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

        println("Working Directory = " + System.getProperty("user.dir"))
        println(Gdx.files.internal("assets/cooper.fnt"))
        labelStyle.font = BitmapFont(Gdx.files.internal("assets/cooper.fnt"))

        val fontGenerator = FreeTypeFontGenerator(Gdx.files.internal("assets/OpenSans.ttf"))
        val fontParameters = FreeTypeFontParameter()
        fontParameters.size = 48
        fontParameters.color = Color.WHITE
        fontParameters.borderWidth = 2f
        fontParameters.borderColor = Color.BLACK
        fontParameters.borderStraight = true
        fontParameters.minFilter = Texture.TextureFilter.Linear
        fontParameters.magFilter = Texture.TextureFilter.Linear

        val customFont = fontGenerator.generateFont(fontParameters)
        labelStyle.font = customFont
    }

    companion object {
        /**
         * Stores reference to game; used when calling setActiveScreen method.
         */
        private var game: BaseGame? = null

        private var labelStyle: LabelStyle = LabelStyle()

        /**
         * Used to switch screens while game is running.
         * Method is static to simplify usage.
         */
        fun setActiveScreen(s: BaseScreen) {
            game?.setScreen(s)
        }
    }

    override fun create() {
        // prepare for multiple classes/stages to receive discrete input
        var im = InputMultiplexer()
        Gdx.input.inputProcessor = im
    }
}
