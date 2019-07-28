package chapter03

import com.badlogic.gdx.Game

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

        /**
         * Used to switch screens while game is running.
         * Method is static to simplify usage.
         */
        fun setActiveScreen(s: BaseScreen) {
            game?.setScreen(s)
        }
    }
}