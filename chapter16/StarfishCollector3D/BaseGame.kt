package chapter16.project3D

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer

abstract class BaseGame : Game() {

    init { game = this }

    companion object {
        private var game: BaseGame? = null
        fun setActiveScreen(s: BaseScreen) { game?.setScreen(s) }
    }

    override fun create() {
        val im = InputMultiplexer()
        Gdx.input.inputProcessor = im
    }
}
