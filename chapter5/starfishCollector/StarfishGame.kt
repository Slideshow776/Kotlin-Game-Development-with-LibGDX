package chapter5

import chapter3.MenuScreen

class StarfishGame: BaseGame() {
    override fun create() {
        setActiveScreen(MenuScreen())
    }
}