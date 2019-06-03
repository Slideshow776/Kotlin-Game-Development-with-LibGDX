package chapter5.starfishCollector

import chapter5.starfishCollector.BaseGame

class StarfishGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
