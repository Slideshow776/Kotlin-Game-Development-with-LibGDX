package chapter15.starfishCollectorShaders

import chapter15.starfishCollectorShaders.BaseGame
import chapter15.starfishCollectorShaders.MenuScreen

class StarfishGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
